package codesquad.springcafe.service.impl;

import codesquad.springcafe.exception.db.ArticleNotFoundException;
import codesquad.springcafe.exception.service.DuplicateArticleIdException;
import codesquad.springcafe.model.Article;
import codesquad.springcafe.model.ListArticle;
import codesquad.springcafe.model.UpdatedArticle;
import codesquad.springcafe.repository.article.ArticleRepository;
import codesquad.springcafe.service.ArticleService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ArticleManagementService implements ArticleService {
    private static final Logger logger = LoggerFactory.getLogger(ArticleManagementService.class);

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleManagementService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public void addArticle(Article article) throws DuplicateArticleIdException {
        try {
            validateDuplicateArticleId(article); // 중복 검증
            articleRepository.addArticle(article);
            logger.info("[게시글 생성 완료] - " + article);
        } catch (DuplicateArticleIdException e) {
            logger.error("이미 중복된 ID를 가진 게시글이 존재합니다.");
            throw new DuplicateArticleIdException(article.getId());
        }
    }

    private void validateDuplicateArticleId(Article article) throws DuplicateArticleIdException {
        try {
            Optional<Article> optArticle = articleRepository.findArticleById(article.getId());
            if (optArticle.isPresent()) {
                throw new DuplicateArticleIdException(optArticle.get().getId());
            }
        } catch (ArticleNotFoundException e) {
            logger.info("게시글 생성 가능");
        }
    }

    @Override
    public Article findArticleById(long id) {
        Optional<Article> optArticle = articleRepository.findArticleById(id);
        return optArticle.orElseThrow(() -> new ArticleNotFoundException(id));
    }

    @Override
    public void updateArticle(long id, UpdatedArticle article) {
        articleRepository.modifyArticle(id, article);
        logger.info("[{} 게시글 수정 성공]", id);
    }

    @Override
    public void deleteArticle(long id) {
        long deletedArticleId = articleRepository.deleteArticle(id);
        logger.info("[{} 게시글 삭제 성공]", deletedArticleId);
    }

    @Override
    public List<ListArticle> findAllArticle() {
        return articleRepository.findAllArticle();
    }

    @Override
    public void increaseViewCount(long id) {
        long increasedViewCount = articleRepository.increaseViewCount(id);
        logger.info("[{}번째 게시글 조회수 증가]", id);
    }

    @Override
    public List<Long> findUserArticleIds(String userId) {
        return articleRepository.findUserArticleIds(userId);
    }
}
