package codesquad.springcafe.service.validator;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.domain.User;
import codesquad.springcafe.domain.repository.ArticleRepository;
import codesquad.springcafe.exception.InvalidAccessException;
import codesquad.springcafe.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleValidator {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleValidator(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void validWriter(User writer, String articleId) {
        Article article = articleRepository.getById(Long.parseLong(articleId)).orElseThrow(() -> new NotFoundException("존재하는 게시글이 없습니다."));
        String actual = article.getWriter();
        String expected = writer.getUserId();
        if (!actual.equals(expected)) {
            throw new InvalidAccessException("접근 권한이 없습니다");
        }
    }
}