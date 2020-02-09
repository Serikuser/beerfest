package by.siarhei.beerfest.command;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.command.CommandProvider;
import by.siarhei.beerfest.command.CommandType;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.*;

import javax.servlet.http.HttpServletRequest;

public class CommandProviderTest {
    private static final String PARAMETER_COMMAND = "command";
    private static final String LOGOUT_COMMAND = "LOGOUT";
    @Mock
    HttpServletRequest request;

    @BeforeClass
    public void setUp() {
    }

    @BeforeMethod
    void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterClass
    public void setDown() {
        request = null;
    }

    @DataProvider
    public Object[][] correctCommand() {
        return new Object[][]{
                {"login", CommandType.LOGIN.getCurrentCommand()},
                {"logout", CommandType.LOGOUT.getCurrentCommand()},
                {"change_locale", CommandType.CHANGE_LOCALE.getCurrentCommand()},
                {"signup", CommandType.SIGNUP.getCurrentCommand()},
                {"submit_bar", CommandType.SUBMIT_BAR.getCurrentCommand()},
                {"participant_list_update", CommandType.PARTICIPANT_LIST_UPDATE.getCurrentCommand()},
                {"make_book", CommandType.MAKE_BOOK.getCurrentCommand()},
                {"signup", CommandType.SIGNUP.getCurrentCommand()},
                {"profile", CommandType.PROFILE.getCurrentCommand()},
                {"add_news", CommandType.ADD_NEWS.getCurrentCommand()},
                {"change_password", CommandType.CHANGE_PASSWORD.getCurrentCommand()}};
    }

    @Test(dataProvider = "correctCommand", description = "providing new login command test")
    public void defineCommandPositiveTest(String input, ActionCommand expected) {
        //given
        Mockito.when(request.getParameter(PARAMETER_COMMAND)).thenReturn(input);
        //when
        ActionCommand actual = CommandProvider.defineCommand(request);
        //then
        Assert.assertEquals(actual, expected);
    }

    @Test(description = "providing new login command test")
    public void defineCommandNegativeTest() {
        //given
        Mockito.when(request.getParameter(PARAMETER_COMMAND)).thenReturn(LOGOUT_COMMAND);
        //when
        ActionCommand actual = CommandProvider.defineCommand(request);
        ActionCommand expected = CommandType.LOGIN.getCurrentCommand();
        //then
        Assert.assertNotEquals(actual, expected);
    }
}
