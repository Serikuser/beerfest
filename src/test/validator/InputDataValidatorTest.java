package validator;

import by.siarhei.beerfest.validator.InputDataValidator;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class InputDataValidatorTest {
    private static final String VALID_LOGIN = "serikuser";
    private static final String INVALID_LOGIN = " ";
    private static final String VALID_EMAIL = "test@test";
    private static final String INVALID_EMAIL = " ";
    private static final String VALID_PLACES = "33";
    private static final String INVALID_PLACES = "invalid";
    private static final String VALID_BAR_DESCRIPTION = "test bar description valid input";
    private static final String INVALID_BAR_DESCRIPTION = "invalid description";
    private static final String VALID_BAR_NAME = "test bar name";
    private static final String INVALID_BAR_NAME = " ";
    private static final String VALID_NUMERIC_INPUT_DATA = "20";
    private static final String INVALID_NUMERIC_INPUT_DATA = "23 totally not numeric 23";
    private String login;
    private String eMail;
    private String places;
    private String barDescription;
    private String barName;
    private String inputData;
    private InputDataValidator inputDataValidator;

    @BeforeClass
    void setUp() {
        inputDataValidator = new InputDataValidator();
    }

    @AfterClass
    void setDown() {
        inputDataValidator = null;
        login = null;
        eMail = null;
        places = null;
        barDescription = null;
        barName = null;
        inputData = null;
    }

    @Test(description = "valid login validation (max length 20 min length 3); input type = String")
    public void isLoginValidPositiveTest() {
        //given
        login = VALID_LOGIN;
        //when
        boolean actual = inputDataValidator.isLoginValid(login);
        //then
        Assert.assertTrue(actual);
    }

    @Test(description = "invalid login validation (max length 20 min length 3); input type = String")
    public void isLoginValidNegativeTest() {
        //given
        login = INVALID_LOGIN;
        //when
        boolean actual = inputDataValidator.isLoginValid(login);
        //then
        Assert.assertFalse(actual);
    }

    @Test(description = "valid email validation (max length 50 min length 5); input type = String")
    public void isEmailValidPositiveTest() {
        //given
        eMail = VALID_EMAIL;
        //when
        boolean actual = inputDataValidator.isLoginValid(eMail);
        //then
        Assert.assertTrue(actual);
    }

    @Test(description = "valid email validation (max length 50 min length 5); input type = String")
    public void isEmailValidNegativeTest() {
        //given
        eMail = INVALID_EMAIL;
        //when
        boolean actual = inputDataValidator.isLoginValid(eMail);
        //then
        Assert.assertFalse(actual);
    }


    @Test(description = "valid input places number validation (max 60 min 10); input type = String")
    public void isPlacesValidPositiveTest() {
        //given
        places = VALID_PLACES;
        //when
        boolean actual = inputDataValidator.isValidPlaces(places);
        //then
        Assert.assertTrue(actual);
    }

    @Test(description = "valid input places number validation (max 60 min 10); input type = String")
    public void isPlacesValidNegativeTest() {
        //given
        places = INVALID_PLACES;
        //when
        boolean actual = inputDataValidator.isValidPlaces(places);
        //then
        Assert.assertFalse(actual);
    }

    @Test(description = "valid description validation (max 100 min 20); input type = String")
    public void isBarDescriptionValidPositiveTest() {
        //given
        barDescription = VALID_BAR_DESCRIPTION;
        //when
        boolean actual = inputDataValidator.isBarDescriptionValid(barDescription);
        //then
        Assert.assertTrue(actual);
    }

    @Test(description = "invalid description validation (max 100 min 20); input type = String")
    public void isBarDescriptionValidNegativeTest() {
        //given
        barDescription = INVALID_BAR_DESCRIPTION;
        //when
        boolean actual = inputDataValidator.isBarDescriptionValid(barDescription);
        //then
        Assert.assertFalse(actual);
    }

    @Test(description = "valid name validation (max 50 min 10); input type = String")
    public void isBarNameValidPositiveTest() {
        //given
        barName = VALID_BAR_NAME;
        //when
        boolean actual = inputDataValidator.isBarNameValid(barName);
        //then
        Assert.assertTrue(actual);
    }

    @Test(description = "invalid name validation (max 50 min 10); input type = String")
    public void isBarNameValidNegativeTest() {
        //given
        barName = INVALID_BAR_NAME;
        //when
        boolean actual = inputDataValidator.isBarNameValid(barName);
        //then
        Assert.assertFalse(actual);
    }

    @Test(description = "input numeric data validation; input type = String")
    public void isNumericValidPositiveTest() {
        //given
        inputData = VALID_NUMERIC_INPUT_DATA;
        //when
        boolean actual = inputDataValidator.isNumeric(inputData);
        //then
        Assert.assertTrue(actual);
    }

    @Test(description = "input numeric data validation; input type = String")
    public void isNumericValidNegativeTest() {
        //given
        inputData = INVALID_NUMERIC_INPUT_DATA;
        //when
        boolean actual = inputDataValidator.isNumeric(inputData);
        //then
        Assert.assertFalse(actual);
    }
}
