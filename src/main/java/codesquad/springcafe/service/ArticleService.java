package codesquad.springcafe.service;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.dto.ArticleDto;
import codesquad.springcafe.error.exception.ArticleNotFoundException;
import codesquad.springcafe.repository.article.ArticleRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void createArticle(Article article) {
        articleRepository.createArticle(article);
        logger.debug("ID {} 게시글 생성", article.getArticleId());
    }

    public List<Article> findAllArticles() {
        return articleRepository.findAllArticles();
    }

    public Article findByArticleId(long articleId) {
        Article article = articleRepository.findByArticleId(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId + " ID 게시글이 존재하지 않습니다."));

        logger.debug("ID {} 게시글 조회", articleId);
        return article;
    }

    public void updateViews(long articleId) {
        articleRepository.updateViews(articleId);
        logger.debug("ID {} 게시글 조회수 증가", articleId);
    }

    public void updateArticle(long articleId, ArticleDto articleDto) {
        articleRepository.updateArticle(articleId, articleDto);
        logger.debug("ID {} 게시글 수정", articleId);
    }

    public void deleteArticle(long articleId) {
        articleRepository.deleteArticle(articleId);
        logger.debug("ID {} 게시글 삭제", articleId);
    }
}