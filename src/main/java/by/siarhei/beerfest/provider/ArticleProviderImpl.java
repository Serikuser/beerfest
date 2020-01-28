package by.siarhei.beerfest.provider;

import by.siarhei.beerfest.entity.impl.Article;

public class ArticleProviderImpl {
    private static ArticleProviderImpl instance;

    private ArticleProviderImpl() {
    }

    public static ArticleProviderImpl getInstance() {
        if (instance == null) {
            instance = new ArticleProviderImpl();
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
}
