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

public class AddNewsCommandTest {
    public static final String ATTRIBUTE_USER_ROLE = "userRole";
    public static final String JSP_ADD_NEWS = "/jsp/admin/newsAdd.jsp";
    public static final String JSP_MAIN = "/jsp/main.jsp";
    private ActionCommand command;
    @Mock
    private SessionRequestContent content;

    @BeforeClass
    public void setUp() {
        command = new AddNewsCommand();
    }

    @BeforeMethod
    void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterClass
    public void setDown() {
        command = null;
        content = null;
    }

    @DataProvider
    public Object[][] router() {
        return new Object[][]{
                {RoleType.ADMIN, new Router(JSP_ADD_NEWS)},
                {RoleType.GUEST, new Router(JSP_MAIN)},
                {RoleType.PARTICIPANT, new Router(JSP_MAIN)},
                {RoleType.UNAUTHORIZED, new Router(JSP_MAIN)}
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
