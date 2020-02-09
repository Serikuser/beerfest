package by.siarhei.beerfest.validator;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.http.Part;

public class UploadFileValidatorTest {
    private static final long VALID_SIZE = 500_000L;
    private static final long INVALID_SIZE = 5_000_000L;
    private static final String FILE_JPG = "file.jpg";
    private static final String VALID_FILE_NAME = FILE_JPG;
    private static final String INVALID_FILE_NAME = "file.zip";
    private String fileName;
    private UploadFileValidator uploadFileValidator;
    @Mock
    private Part part;

    @BeforeClass
    public void setUp() {
        uploadFileValidator = new UploadFileValidator();
    }

    @BeforeMethod
    void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterClass
    public void setDown() {
        fileName = null;
        uploadFileValidator = null;
        part = null;
    }

    @Test(description = "valid input avatar file validation(max size 524_288b, allowed types .jpg, .gif, .png); input type = String fileName, Part filePart")
    public void isAllowedAvatarImagePositiveTest() {
        //given
        Mockito.when(part.getSize()).thenReturn(VALID_SIZE);
        fileName = VALID_FILE_NAME;
        //when
        boolean actual = uploadFileValidator.isAllowedAvatarImage(fileName, part);
        //then
        Assert.assertTrue(actual);
    }

    @Test(description = "invalid size input avatar file validation(max size 524_288b, allowed types .jpg, .gif, .png); input type = String fileName, Part filePart")
    public void isAllowedAvatarImageSizeNegativeTest() {
        //given
        Mockito.when(part.getSize()).thenReturn(INVALID_SIZE);
        fileName = VALID_FILE_NAME;
        //when
        boolean actual = uploadFileValidator.isAllowedAvatarImage(fileName, part);
        //then
        Assert.assertFalse(actual);
    }

    @Test(description = "invalid size input avatar file validation(max size 524_288b, allowed types .jpg, .gif, .png); input type = String fileName, Part filePart")
    public void isAllowedAvatarImageTypeNegativeTest() {
        //given
        Mockito.when(part.getSize()).thenReturn(VALID_SIZE);
        fileName = INVALID_FILE_NAME;
        //when
        boolean actual = uploadFileValidator.isAllowedAvatarImage(fileName, part);
        //then
        Assert.assertFalse(actual);
    }

    @Test(description = "valid input feed file validation(max size 4_000_000b, allowed types .jpg, .gif, .png); input type = String fileName, Part filePart")
    public void isAllowedFeedImagePositiveTest() {
        //given
        Mockito.when(part.getSize()).thenReturn(VALID_SIZE);
        fileName = VALID_FILE_NAME;
        //when
        boolean actual = uploadFileValidator.isAllowedFeedImage(fileName, part);
        //then
        Assert.assertTrue(actual);
    }

    @Test(description = "invalid size input feed file validation(max size 4_000_000b, allowed types .jpg, .gif, .png); input type = String fileName, Part filePart")
    public void isAllowedFeedImageSizeNegativeTest() {
        //given
        Mockito.when(part.getSize()).thenReturn(INVALID_SIZE);
        fileName = VALID_FILE_NAME;
        //when
        boolean actual = uploadFileValidator.isAllowedFeedImage(fileName, part);
        //then
        Assert.assertFalse(actual);
    }

    @Test(description = "invalid size input feed file validation(max size 4_000_000b, allowed types .jpg, .gif, .png); input type = String fileName, Part filePart")
    public void isAllowedFeedImageTypeNegativeTest() {
        //given
        Mockito.when(part.getSize()).thenReturn(VALID_SIZE);
        fileName = INVALID_FILE_NAME;
        //when
        boolean actual = uploadFileValidator.isAllowedFeedImage(fileName, part);
        //then
        Assert.assertFalse(actual);
    }
}
