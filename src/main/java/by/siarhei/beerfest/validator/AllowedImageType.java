package by.siarhei.beerfest.validator;

public enum AllowedImageType {
    JPG(".jpg"),
    GIF(".gif"),
    PNG(".png");
    private String extension;

    AllowedImageType(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}
