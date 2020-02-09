package by.siarhei.beerfest.entity.impl.provider;

import by.siarhei.beerfest.entity.impl.Article;

public class ArticleProvider {
    private static ArticleProvider instance;

    private ArticleProvider() {
    }

    public static ArticleProvider getInstance() {
        if (instance == null) {
            instance = new ArticleProvider();
        }
        return instance;
    }

    public Article create(long id, String title, String text, String imgSrc) {
        Article article = new Article();
        article.setId(id);
        article.setTitle(title);
        article.setText(text);
        article.setImgSrc(imgSrc);
        return article;
    }

    public Article create(String title, String text, String imgSrc) {
        Article article = new Article();
        article.setTitle(title);
        article.setText(text);
        article.setImgSrc(imgSrc);
        return article;
    }
}
