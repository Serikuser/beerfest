package by.siarhei.beerfest.provider;

import by.siarhei.beerfest.entity.impl.Bar;
import by.siarhei.beerfest.entity.impl.provider.BarProvider;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BarProviderTest {
    private static final int TEST_ID = 22;
    private static final int TEST_ACCOUNT_ID = 23;
    private static final String TEST_NAME = "test name";
    private static final String TEST_DESCRIPTION = "test description";
    private static final int TEST_FOOD_ID = 24;
    private static final String TEST_FOOD_NAME = "test food name";
    private static final int TEST_BEER_ID = 25;
    private static final String TEST_BEER_NAME = "test beer name";
    private static final int TEST_PLACES = 3;
    private BarProvider barProvider;

    @BeforeClass
    public void setUp() {
        barProvider = BarProvider.getInstance();
    }

    @AfterClass
    public void setDown() {
        barProvider = null;
    }


    @Test(description =
            "providing new entity; " +
                    "input type = long barId, long accountId, String name, " +
                    "String description, long foodId, String foodName, long beerId, String beerName, int places;")
    public void createPositiveTest() {
        //given
        Bar expected = new Bar();
        expected.setId(TEST_ID);
        expected.setAccountId(TEST_ACCOUNT_ID);
        expected.setName(TEST_NAME);
        expected.setDescription(TEST_DESCRIPTION);
        expected.setFoodId(TEST_FOOD_ID);
        expected.setFoodName(TEST_FOOD_NAME);
        expected.setBeerId(TEST_BEER_ID);
        expected.setBeerName(TEST_BEER_NAME);
        expected.setPlaces(TEST_PLACES);
        //when
        Bar actual = barProvider.create(
                TEST_ID, TEST_ACCOUNT_ID, TEST_NAME, TEST_DESCRIPTION,
                TEST_FOOD_ID, TEST_FOOD_NAME, TEST_BEER_ID, TEST_BEER_NAME, TEST_PLACES);
        //then
        Assert.assertEquals(actual, expected);
    }

    @Test(description =
            "providing new entity; " +
                    "input type = long barId, long accountId, String name, " +
                    "String description, long foodId, String foodName, long beerId, String beerName, int places;")
    public void createNegativeTest() {
        //given
        Bar expected = new Bar();
        expected.setAccountId(TEST_ACCOUNT_ID);
        expected.setName(TEST_NAME);
        expected.setDescription(TEST_DESCRIPTION);
        expected.setFoodId(TEST_FOOD_ID);
        expected.setFoodName(TEST_FOOD_NAME);
        expected.setBeerId(TEST_BEER_ID);
        expected.setBeerName(TEST_BEER_NAME);
        expected.setPlaces(TEST_PLACES);
        //when
        Bar actual = barProvider.create(
                TEST_ID, TEST_ACCOUNT_ID, TEST_NAME, TEST_DESCRIPTION,
                TEST_FOOD_ID, TEST_FOOD_NAME, TEST_BEER_ID, TEST_BEER_NAME, TEST_PLACES);
        //then
        Assert.assertNotEquals(actual, expected);
    }
}
