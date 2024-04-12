package codesquad.springcafe.service.impl;

import codesquad.springcafe.dto.Article;
import codesquad.springcafe.dto.UpdatedArticle;
import codesquad.springcafe.exception.db.ArticleNotFoundException;
import codesquad.springcafe.exception.service.DuplicateArticleIdException;
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
    public void addArticle(Article article) {
        try {
            validateDuplicateArticleId(article); // 중복 검증
            articleRepository.addArticle(article);
            logger.info("[게시글 생성 완료] - " + article);
        } catch (DuplicateArticleIdException e) {
            logger.error("이미 중복된 ID를 가진 게시글이 존재합니다.");
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
    public Optional<Article> findArticleById(long id) {
        Optional<Article> optionalArticle = Optional.empty();
        try {
            optionalArticle = articleRepository.findArticleById(id);
        } catch (ArticleNotFoundException e) {
            logger.error("게시글이 존재하지 않습니다.");
        }
        return optionalArticle;
    }

    @Override
    public void modifyArticle(long id, UpdatedArticle article) {
        try {
            articleRepository.modifyArticle(id, article);
        } catch (ArticleNotFoundException e) {
            logger.error("게시글이 존재하지 않습니다.");
        }
    }

    @Override
    public void deleteArticle(long id) {
        long deletedArticleId = articleRepository.deleteArticle(id);
        logger.info("[" + deletedArticleId + "번째 게시글 삭제 성공]");
    }

    @Override
    public List<Article> findAllArticle() {
        return articleRepository.findAllArticle();
    }

    @Override
    public void increaseViewCount(long id) {
        try {
            long increasedViewCount = articleRepository.increaseViewCount(id);
            logger.info("[" + id + "번째 게시글 조회수 증가] - " + increasedViewCount);
        } catch (ArticleNotFoundException e) {
            logger.error("게시글이 존재하지 않습니다.");
        }
    }
}
