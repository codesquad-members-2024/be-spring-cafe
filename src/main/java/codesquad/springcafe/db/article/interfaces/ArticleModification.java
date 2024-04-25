package codesquad.springcafe.db.article.interfaces;

import codesquad.springcafe.model.article.Article;

public interface ArticleModification {
    public void addArticle(Article article);
    public void update(long sequence, Article updatedArticle);
    public void clearDatabase();
}
