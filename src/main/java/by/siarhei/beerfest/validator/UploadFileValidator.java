package by.siarhei.beerfest.validator;

import javax.servlet.http.Part;
import java.util.stream.Stream;

public class UploadFileValidator {
    private static final long MAX_FILE_SIZE = 524288;

    public boolean isAvatar(String fileName, Part filePart) {
        boolean flag;
        long size = filePart.getSize();
        String extension = fileName.substring(fileName.length() - 3).toUpperCase();
        flag = Stream.of(AllowedImageTypes.values()).anyMatch(allowedImageTypes -> allowedImageTypes.name().equalsIgnoreCase(extension))
                && size <= MAX_FILE_SIZE;
        return flag;
    }

    public boolean isFeedImage(String fileName, Part filePart) {
        // TODO: 19.01.2020
        return false;
    }
}
