package by.siarhei.beerfest.dao;

import java.sql.Connection;

public abstract class DaoTransaction{
    protected Connection connection;

    void setConnection(Connection connection) {
        this.connection = connection;
    }
}
