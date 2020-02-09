package by.siarhei.beerfest.service;

import by.siarhei.beerfest.command.LocaleType;
import by.siarhei.beerfest.service.impl.LanguageServiceImpl;
import by.siarhei.beerfest.servlet.SessionRequestContent;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LanguageServiceImplTest {
    private static final String ATTRIBUTE_LOCALE = "locale";
    public static final String BY = "BY";
    public static final String INVALID_LOCALE = "invalid";
    public static final String EN = "EN";

    @Mock
    private HttpServletRequest request;
    @Mock
    private SessionRequestContent content;
    @Mock
    private HttpSession session;
    private LanguageService languageService;

    @BeforeClass
    public void setUp() {
        languageService = new LanguageServiceImpl();
    }

    @BeforeMethod
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterClass
    public void setDown() {
        request = null;
        content = null;
        session = null;
        languageService = null;
    }

    @Test(description = "define locale from content with invalid locale type;" +
            " input type = SessionRequestContent; output = LocaleType")
    public void defineLocaleFromContentInvalidLocaleTypeTest() {
        //given
        Mockito.when(content.getSessionAttribute(ATTRIBUTE_LOCALE)).thenReturn(INVALID_LOCALE).thenReturn(INVALID_LOCALE);
        //when
        LocaleType actual = languageService.defineLocale(content);
        LocaleType expected = LocaleType.RU;
        //then
        Assert.assertEquals(actual, expected);
    }

    @Test(description = "define locale from content with valid locale type;" +
            " input type = SessionRequestContent; output = LocaleType")
    public void defineLocaleFromContentValidLocaleTypeTest() {
        //given
        Mockito.when(content.getSessionAttribute(ATTRIBUTE_LOCALE)).thenReturn(EN).thenReturn(EN);
        //when
        LocaleType actual = languageService.defineLocale(content);
        LocaleType expected = LocaleType.EN;
        //then
        Assert.assertEquals(actual, expected);
    }

    @Test(description = "define locale from request with invalid locale type;" +
            " input type = SessionRequestContent; output = LocaleType")
    public void defineLocaleFromRequestInvalidLocaleTypeTest() {
        //given
        Mockito.when(request.getSession()).thenReturn(session).thenReturn(session);
        Mockito.when(session.getAttribute(ATTRIBUTE_LOCALE)).thenReturn(INVALID_LOCALE).thenReturn(INVALID_LOCALE);
        //when
        LocaleType actual = languageService.defineLocale(request);
        LocaleType expected = LocaleType.RU;
        //then
        Assert.assertEquals(actual, expected);
    }

    @Test(description = "define locale from request with valid locale type;" +
            " input type = SessionRequestContent; output = LocaleType")
    public void defineLocaleFromRequestValidLocaleTypeTest() {
        //given
        Mockito.when(request.getSession()).thenReturn(session).thenReturn(session);
        Mockito.when(session.getAttribute(ATTRIBUTE_LOCALE)).thenReturn(BY).thenReturn(BY);
        //when
        LocaleType actual = languageService.defineLocale(request);
        LocaleType expected = LocaleType.BY;
        //then
        Assert.assertEquals(actual, expected);
    }
}
