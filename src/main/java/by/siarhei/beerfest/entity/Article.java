package by.siarhei.beerfest.entity;

public class Article extends Entity {
    private String title;
    private String text;
    private String imgSrc;

    public Article() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Article article = (Article) o;
        if (title != null ? !title.equals(article.title) : article.title != null) {
            return false;
        }
        if (text != null ? !text.equals(article.text) : article.text != null) {
            return false;
        }
        return imgSrc != null ? imgSrc.equals(article.imgSrc) : article.imgSrc == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (imgSrc != null ? imgSrc.hashCode() : 0);
        return result;
    }
}

