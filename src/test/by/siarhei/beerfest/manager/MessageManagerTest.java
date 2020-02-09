package by.siarhei.beerfest.manager;

import by.siarhei.beerfest.command.CommandType;
import by.siarhei.beerfest.command.LocaleType;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MessageManagerTest {
    private String expected;

    @AfterClass
    public void setDown() {
        expected = null;
    }

    @DataProvider
    public Object[][] ruMessages() {
        return new Object[][]{
                {"message.login.banned.error", "Ваш аккаунт забанен"},
                {"message.signup.error.joke", "АТЯ-ТЯ-ТЯ-ТЯ"},
                {"message.submit.bar.error", "Вы уже зарегестрировали свой бар"},
                {"message.login.error", "Аккаунт с такими данными не найден"}};
    }

    @Test(
            dataProvider = "ruMessages",
            description = "locale-specific messages; input type = String key, LocaleType localeType; output = String")

    public void getMessageTest(String key, String message) {
        //given
        expected = message;
        //when
        String actual = MessageManager.getProperty(key, LocaleType.RU);
        //then
        Assert.assertEquals(actual, expected);
    }
}
