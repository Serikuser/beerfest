package by.siarhei.beerfest.dao;

import by.siarhei.beerfest.connection.ConnectionPool;

import java.sql.Connection;

public abstract class DaoTransaction {
    private Connection connection;
    protected boolean inTransaction;

    public void setInTransaction() {
        this.inTransaction = true;
    }

    protected Connection getConnection() {
        if (connection == null) {
            return ConnectionPool.INSTANCE.getConnection();
        } else {
            return connection;
        }
    }

    void setConnection(Connection connection) {
        this.connection = connection;
    }
}
