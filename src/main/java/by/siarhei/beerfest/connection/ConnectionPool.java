package by.siarhei.beerfest.connection;

import by.siarhei.beerfest.exception.NotProxyConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public enum ConnectionPool {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    private static final long MINUTES_10 = 600_000L;
    private TimerTask poolConsistencyObserver;
    private BlockingQueue<Connection> freeConnections;
    private Queue<Connection> occupiedConnections;
    private static final int POOL_SIZE = 48;

    ConnectionPool() {
        init();
        checkPoolConsistency();
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = freeConnections.take();
            occupiedConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.error(String.format("Connections cant be taken from pool throws exception: %s", e));
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) throws NotProxyConnectionException {
        if (connection instanceof ProxyConnection) {
            occupiedConnections.remove(connection);
            try {
                if (!connection.getAutoCommit()) {
                    connection.setAutoCommit(true);
                }
                freeConnections.offer(connection);
            } catch (SQLException e) {
                logger.error(String.format("Cant set auto commit mode to true on connection: %s", connection));
                ((ProxyConnection) connection).reallyClose();
            }
        } else throw new NotProxyConnectionException("Connection not from pool");
    }

    public void destroyPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                ((ProxyConnection) freeConnections.take()).reallyClose();
            } catch (InterruptedException e) {
                logger.error(String.format("Connections cannot be taken form freeConnections pool throws exception: %s", e));
            }
        }
        poolConsistencyObserver.cancel();
        deregisterDrivers();
        logger.info(String.format("Poll: %s destroyed", this.toString()));
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error(String.format("Driver Manager cant deregister driver throws exception: %s", e));
            }
        });
    }

    private void init() {
        freeConnections = new LinkedBlockingDeque<>(POOL_SIZE);
        occupiedConnections = new ArrayDeque<>();
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                ProxyConnection connection = ConnectionProvider.getConnection();
                freeConnections.offer(connection);
            } catch (SQLException e) {
                throw new ExceptionInInitializerError(String.format("Poll cant be filled with reason %s", e));
            }
        }
    }

    private void checkPoolConsistency() {
        if (poolConsistencyObserver == null) {
            poolConsistencyObserver = new TimerTask() {
                @Override
                public void run() {
                    while ((occupiedConnections.size() + freeConnections.size()) < POOL_SIZE) {
                        try {
                            Connection connection = ConnectionProvider.getConnection();
                            freeConnections.offer(connection);
                        } catch (SQLException e) {
                            throw new ExceptionInInitializerError(String.format("Poll cant be filled with reason %s", e));
                        }
                    }
                }
            };
            Timer timer = new Timer("Pool Consistency Observer");
            timer.scheduleAtFixedRate(poolConsistencyObserver, MINUTES_10, MINUTES_10);
        }
    }

    @Override
    public String toString() {
        return String.format("Custom pool on %s connections", POOL_SIZE);
    }
}
