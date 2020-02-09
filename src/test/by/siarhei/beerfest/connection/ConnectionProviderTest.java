package by.siarhei.beerfest.connection;

import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;

public class ConnectionProviderTest {
    private ProxyConnection connection;


    @BeforeMethod
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterClass
    public void setDown() {
        connection.reallyClose();
    }

    @Test(description = "providing Proxy connection to db from standard connection")
    public void getProxyConnection() throws SQLException {
        //given
        connection = ConnectionProvider.getConnection();
        //when
        Class<?> actual = connection.getClass();
        Class<?> expected = ProxyConnection.class;
        //then
        Assert.assertEquals(actual, expected);
    }
}
