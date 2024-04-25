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
        logger.debug("ID {} 게시글 생성", article.getId());
    }

    public List<Article> findAllArticles() {
        return articleRepository.findAllArticles();
    }

    public Article findById(long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id + " ID 게시글이 존재하지 않습니다."));

        logger.debug("ID {} 게시글 조회", id);
        return article;
    }

    public void updateViews(long id) {
        articleRepository.updateViews(id);
        logger.debug("ID {} 게시글 조회수 증가", id);
    }

    public void updateArticle(long id, ArticleDto articleDto) {
        articleRepository.updateArticle(id, articleDto);
        logger.debug("ID {} 게시글 수정", id);
    }

    public void deleteArticle(long id) {
        articleRepository.deleteArticle(id);
        logger.debug("ID {} 게시글 삭제", id);
    }
}