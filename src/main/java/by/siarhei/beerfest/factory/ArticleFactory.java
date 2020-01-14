package by.siarhei.beerfest.factory;

import by.siarhei.beerfest.entity.Article;
import by.siarhei.beerfest.entity.Entity;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ArticleFactory {
    // FIXME: 07.01.2020
    private static ArticleFactory instance;
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    private static Lock locker = new ReentrantLock();

    private ArticleFactory() {
    }

    public static ArticleFactory getInstance() {
        if (!isCreated.get()) {
            try {
                locker.lock();
                if (instance == null) {
                    instance = new ArticleFactory();
                    isCreated.set(true);
                }
            } finally {
                locker.unlock();
            }
        }
        return instance;
    }

    public Entity create(long id, String title, String text, String imgSrc) {
        Article article = new Article();
        article.setId(id);
        article.setTitle(title);
        article.setText(text);
        article.setImgSrc(imgSrc);
        return article;
    }
}
