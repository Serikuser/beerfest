package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.command.Router;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.servlet.SessionRequestContent;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.*;

public class ChangeLocaleCommandTest {
    public static final String ATTRIBUTE_USER_ROLE = "userRole";
    public static final String JSP_MAIN = "/jsp/main.jsp";
    private ActionCommand command;
    private Router router;
    @Mock
    private SessionRequestContent content;

    @BeforeClass
    public void setUp() {
        router = new Router(JSP_MAIN);
        router.setRedirect();
        command = new ChangeLocaleCommand();
    }

    @BeforeMethod
    void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterClass
    public void setDown() {
        command = null;
        content = null;
        router = null;
    }

    @DataProvider
    public Object[][] router() {
        return new Object[][]{
                {RoleType.ADMIN, router},
                {RoleType.GUEST, router},
                {RoleType.PARTICIPANT, router},
                {RoleType.UNAUTHORIZED, router}
        };
    }

    @Test(
            dataProvider = "router",
            description = "command correct input data execution; " +
                    "input type = SessionRequestContent content; output type = Router")
    void executeTest(RoleType role, Router expected) {
        //given
        Mockito.when(content.getSessionAttribute(ATTRIBUTE_USER_ROLE)).thenReturn(role);
        //when
        Router actual = command.execute(content);
        //then
        Assert.assertEquals(actual, expected);
    }
}
