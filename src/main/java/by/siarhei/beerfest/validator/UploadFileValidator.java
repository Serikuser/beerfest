package by.siarhei.beerfest.validator;

import javax.servlet.http.Part;
import java.util.stream.Stream;

public class UploadFileValidator {
    private static final long MAX_AVATAR_SIZE = 524_288;
    private static final long MAX_NEWS_IMAGE_SIZE = 4_000_000;
    private static final char EXTENSION_SPLIT_CHAR = '.';

    public boolean isAllowedAvatarImage(String fileName, Part filePart) {
        long size = filePart.getSize();
        String extension = fileName.substring(fileName.lastIndexOf(EXTENSION_SPLIT_CHAR));
        return Stream.of(AllowedImageType.values()).anyMatch(allowedImageType -> allowedImageType.getExtension().equalsIgnoreCase(extension))
                && size <= MAX_AVATAR_SIZE;
    }

    public boolean isAllowedFeedImage(String fileName, Part filePart) {
        long size = filePart.getSize();
        String extension = fileName.substring(fileName.lastIndexOf(EXTENSION_SPLIT_CHAR));
        return  Stream.of(AllowedImageType.values()).anyMatch(allowedImageType -> allowedImageType.getExtension().equalsIgnoreCase(extension))
                && size <= MAX_NEWS_IMAGE_SIZE;
    }
}
