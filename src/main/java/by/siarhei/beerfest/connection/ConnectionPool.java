package by.siarhei.beerfest.connection;

import by.siarhei.beerfest.exception.NotProxyConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public enum ConnectionPool {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();

    private BlockingQueue<Connection> freeConnections;
    private Queue<Connection> occupiedConnections;
    private static final int POOL_SIZE = 48;

    ConnectionPool() {
        init();
    }

    public ProxyConnection getConnection() {
        Connection connection = null;
        try {
            connection = freeConnections.take();
            occupiedConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.error(String.format("Connections cant be taken from pool throws exception: %s", e));
        }
        return (ProxyConnection) connection;
    }

    public void releaseConnection(Connection connection) throws NotProxyConnectionException {
        if (connection.getClass() == ProxyConnection.class) {
            occupiedConnections.remove(connection);
            freeConnections.offer(connection);
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
                logger.fatal(String.format("Poll cant be filled throws exception: %s", e));
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Custom pool on %s: connections", POOL_SIZE);
    }
}
