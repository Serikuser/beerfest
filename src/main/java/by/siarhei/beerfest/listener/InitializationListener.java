package by.siarhei.beerfest.listener;

import by.siarhei.beerfest.connection.ConnectionPool;
import by.siarhei.beerfest.service.impl.RegistrationServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class InitializationListener implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        RegistrationServiceImpl.getInstance();
        ConnectionPool connectionPool = ConnectionPool.INSTANCE;
        logger.info(String.format("%s is on duty ", connectionPool.toString()));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool.INSTANCE.destroyPool();
    }
}
