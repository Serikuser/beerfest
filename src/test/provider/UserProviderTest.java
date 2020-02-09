package provider;

import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.entity.StatusType;
import by.siarhei.beerfest.entity.impl.User;
import by.siarhei.beerfest.entity.impl.provider.UserProvider;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserProviderTest {
    private static final int TEST_ID = 1;
    private static final String TEST_LOGIN = "test login";
    private static final String TEST_PASSWORD = "test password";
    private static final String TEST_EMAIL = "test@email";
    private static final String TEST_IMG = "/img/test.gif";
    private UserProvider userProvider;

    @BeforeClass
    public void setUp() {
        userProvider = UserProvider.getInstance();
    }

    @AfterClass
    public void setDown() {
        userProvider = null;
    }

    @Test(description =
            "providing new entity; " +
                    "input type = String login, String password, String email, " +
                    "String avatarUrl, RoleType role, StatusType status ")
    public void createPositiveTest() {
        //given
        User expected = new User();
        expected.setLogin(TEST_LOGIN);
        expected.setPassword(TEST_PASSWORD);
        expected.setEmail(TEST_EMAIL);
        expected.setAvatarUrl(TEST_IMG);
        expected.setRole(RoleType.UNAUTHORIZED);
        expected.setStatus(StatusType.ACTIVE);
        //when
        User actual = userProvider.create(
                TEST_LOGIN, TEST_PASSWORD, TEST_EMAIL,
                TEST_IMG, RoleType.UNAUTHORIZED, StatusType.ACTIVE);
        //then
        Assert.assertEquals(actual, expected);
    }

    @Test(description =
            "providing new entity; " +
                    "input type = String login, String password, String email, " +
                    "String avatarUrl, RoleType role, StatusType status ")
    public void createNegativeTest() {
        //given
        User expected = new User();
        expected.setId(TEST_ID);
        expected.setLogin(TEST_LOGIN);
        expected.setPassword(TEST_PASSWORD);
        expected.setEmail(TEST_EMAIL);
        expected.setAvatarUrl(TEST_IMG);
        expected.setRole(RoleType.UNAUTHORIZED);
        expected.setStatus(StatusType.ACTIVE);
        //when
        User actual = userProvider.create(
                TEST_LOGIN, TEST_PASSWORD, TEST_EMAIL,
                TEST_IMG, RoleType.UNAUTHORIZED, StatusType.ACTIVE);
        //then
        Assert.assertNotEquals(actual, expected);
    }
}
