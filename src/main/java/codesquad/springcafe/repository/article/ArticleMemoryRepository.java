package codesquad.springcafe.repository.article;

import codesquad.springcafe.exception.db.ArticleNotFoundException;
import codesquad.springcafe.model.Article;
import codesquad.springcafe.model.ListArticle;
import codesquad.springcafe.model.UpdatedArticle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArticleMemoryRepository implements ArticleRepository {
    private static final Logger logger = LoggerFactory.getLogger(ArticleMemoryRepository.class);
    private static List<Article> articles = new ArrayList<>();

    @Override
    public Article addArticle(Article article) {
        long id = articles.size() + 1;
        article.setId(id);
        articles.add(article);
        logger.debug(article + " MemoryDB 저장 완료");

        return article;
    }

    @Override
    public Optional<Article> findArticleById(long id) throws ArticleNotFoundException {
        try {
            return Optional.ofNullable(articles.get((int) id));
        } catch (IndexOutOfBoundsException e) {
            // 게시글을 찾지 못한 경우 ArticleNotFoundException 예외를 던진다.
            throw new ArticleNotFoundException((int) id);
        }
    }

    @Override
    public long modifyArticle(long id, UpdatedArticle article) throws ArticleNotFoundException {
        return id;
    }

    @Override
    public long deleteArticle(long id) {
        articles.remove(id);
        return id;
    }

    @Override
    public List<ListArticle> findAllArticle() {
        return null;
    }

    @Override
    public long increaseViewCount(long id) {
        Article article = articles.get((int) id);
        long increasedViewCount = article.getViewCount() + 1;
        article.setViewCount(increasedViewCount);
        return increasedViewCount;
    }

    @Override
    public List<Long> findUserArticleIds(String userId) {
        return null;
    }
}
