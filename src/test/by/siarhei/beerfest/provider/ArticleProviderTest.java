package by.siarhei.beerfest.provider;

import by.siarhei.beerfest.entity.impl.Article;
import by.siarhei.beerfest.entity.impl.provider.ArticleProvider;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ArticleProviderTest {
    private static final String IMG_TEST_JPG = "/img/test.jpg";
    private static final String TEST_TITLE = "test title";
    private static final String TEST_TEXT = "test text";
    private static final int TEST_ID = 22;
    private ArticleProvider articleProvider;


    @BeforeClass
    public void setUp() {
        articleProvider = ArticleProvider.getInstance();
    }

    @AfterClass
    public void setDown() {
        articleProvider = null;
    }

    @Test(description = "providing new entity with no id input; input type = String title, String text, String imgSrc;")
    public void createNoIdPositiveTest() {
        //given
        Article expected = new Article();
        expected.setImgSrc(IMG_TEST_JPG);
        expected.setTitle(TEST_TITLE);
        expected.setText(TEST_TEXT);
        //when
        Article actual = articleProvider.create(TEST_TITLE, TEST_TEXT, IMG_TEST_JPG);
        //then
        Assert.assertEquals(actual, expected);
    }

    @Test(description = "providing new entity with no id input; input type = String title, String text, String imgSrc;")
    public void createNoIdNegativeTest() {
        //given
        Article expected = new Article();
        expected.setId(TEST_ID);
        expected.setImgSrc(IMG_TEST_JPG);
        expected.setTitle(TEST_TITLE);
        expected.setText(TEST_TEXT);
        //when
        Article actual = articleProvider.create(TEST_TITLE, TEST_TEXT, IMG_TEST_JPG);
        //then
        Assert.assertNotEquals(actual, expected);
    }

    @Test(description = "providing new entity with; input type = String title, String text, String imgSrc;")
    public void createPositiveTest() {
        //given
        Article expected = new Article();
        expected.setId(TEST_ID);
        expected.setImgSrc(IMG_TEST_JPG);
        expected.setTitle(TEST_TITLE);
        expected.setText(TEST_TEXT);
        //when
        Article actual = articleProvider.create(TEST_ID, TEST_TITLE, TEST_TEXT, IMG_TEST_JPG);
        //then
        Assert.assertEquals(actual, expected);
    }
}
