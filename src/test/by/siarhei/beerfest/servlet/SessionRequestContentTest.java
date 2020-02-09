package by.siarhei.beerfest.servlet;

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
import java.util.Enumeration;
import java.util.Iterator;

public class SessionRequestContentTest {
    public static final String TEST_ATTRIBUTE = "testAttribute";
    public static final String TEST_VALUE = "testValue";
    private SessionRequestContent content;
    private String expected;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private Iterator<String> iterator;
    @Mock
    private Enumeration<String> enumeration;

    @BeforeClass
    public void setUp() {
        expected = TEST_VALUE;
    }

    @BeforeMethod
    void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterClass
    public void setDown() {
        content = null;
        request = null;
        enumeration = null;
        expected = null;
        session = null;
        iterator = null;
    }

    @Test(description = "content creation from HttpServletRequest")
    public void contentExtractingTest() {
        //given
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttributeNames()).thenReturn(enumeration).thenReturn(enumeration);
        Mockito.when(enumeration.asIterator()).thenReturn(iterator).thenReturn(iterator);
        Mockito.when(request.getAttributeNames()).thenReturn(enumeration);
        Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false).thenReturn(false);
        Mockito.when(iterator.next()).thenReturn(TEST_ATTRIBUTE);
        Mockito.when(session.getAttribute(TEST_ATTRIBUTE)).thenReturn(TEST_VALUE);
        content = new SessionRequestContent(request);
        //when
        String actual = content.getSessionAttribute(TEST_ATTRIBUTE).toString();
        //then
        Assert.assertEquals(actual, expected);
    }
}
