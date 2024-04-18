package codesquad.springcafe.service.article;

import codesquad.springcafe.controller.article.UpdateArticle;
import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.repository.article.ArticleRepository;
import codesquad.springcafe.service.exception.ResourceNotFoundException;
import codesquad.springcafe.service.exception.UnauthorizedException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleManager implements ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleManager(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article publish(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Optional<Article> findArticle(long id) {
        return articleRepository.findById(id);
    }

    @Override
    public List<Article> findAllArticle() {
        return articleRepository.findAll();
    }

    @Override
    public void validateExists(long id) {
        findArticle(id).orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 게시물입니다. 게시물 아이디: " + id));
    }

    @Override
    public void validateAuthor(String loginId, String author) {
        if (!loginId.equals(author)) {
            throw new UnauthorizedException("작성자와 일치하지 않습니다. 로그인 아이디: " + loginId);
        }
    }

    @Override
    public void editArticle(String loginId, UpdateArticle updateParam) {
        /* 게시물 부존재 검증: 404 에러 */
        validateExists(updateParam.getId());

        /* 작성자 검증: 403 에러 */
        validateAuthor(loginId, updateParam.getCreatedBy());

        articleRepository.update(updateParam);
    }
}
