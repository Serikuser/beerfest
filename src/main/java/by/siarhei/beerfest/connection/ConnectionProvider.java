package by.siarhei.beerfest.connection;

import by.siarhei.beerfest.manager.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
    private static final Logger logger = LogManager.getLogger();

    private static final String PROPERTIES_DATABASE_DRIVER = "database.driver";
    private static final String PROPERTIES_DATABASE_URL = "database.url";
    private static final String PROPERTIES_DATABASE_NAME = "database.name";
    private static final String PROPERTIES_DATABASE_ROOT_USER = "database.root.user";
    private static final String PROPERTIES_DATABASE_ROOT_PASSWORD = "database.root.password";

    static {
        String databaseDriverUrl = ConfigurationManager.getProperty(PROPERTIES_DATABASE_DRIVER);
        try {
            Class.forName(databaseDriverUrl);
        } catch (ClassNotFoundException e) {
            logger.fatal(String.format("Poll cant register drivers throws exception: %s", e));
            throw new ExceptionInInitializerError("Cant install database drivers");
        }
    }

    private ConnectionProvider() {
    }

    static ProxyConnection getConnection() throws SQLException {
        String databaseUrl = ConfigurationManager.getProperty(PROPERTIES_DATABASE_URL) + ConfigurationManager.getProperty(PROPERTIES_DATABASE_NAME);
        String userName = ConfigurationManager.getProperty(PROPERTIES_DATABASE_ROOT_USER);
        String userPassword = ConfigurationManager.getProperty(PROPERTIES_DATABASE_ROOT_PASSWORD);
        return new ProxyConnection(
                DriverManager.getConnection(databaseUrl, userName, userPassword));
    }
}
