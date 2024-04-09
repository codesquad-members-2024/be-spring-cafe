package codesquad.springcafe.repository.impl;

import codesquad.springcafe.dto.Article;
import codesquad.springcafe.exception.ArticleNotFoundException;
import codesquad.springcafe.repository.ArticleRepository;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleMemoryRepository implements ArticleRepository {
    private static final Logger logger = LoggerFactory.getLogger(ArticleMemoryRepository.class);
    private static List<Article> articles = new ArrayList<>();

    @Override
    public int addArticle(Article article) {
        int id = articles.size() + 1;
        article.setId(id);
        articles.add(article);
        logger.debug(article + " MemoryDB 저장 완료");

        return id;
    }

    @Override
    public Article findArticleById(int id) throws Exception {
        try {
            return articles.get(id);
        } catch (IndexOutOfBoundsException e) {
            // 게시글을 찾지 못한 경우 ArticleNotFoundException 예외를 던진다.
            throw new ArticleNotFoundException(id);
        }
    }

    @Override
    public Article modifyArticle(Article article) throws Exception {
        return null;
    }

    @Override
    public Article deleteArticle(Article article) {
        return null;
    }

    @Override
    public List<Article> findAllArticle() {
        return articles;
    }

    @Override
    public int increaseViewCount(Article article) {
        int increasedViewCount = article.getViewCount() + 1;
        article.setViewCount(increasedViewCount);
        return increasedViewCount;
    }
}
