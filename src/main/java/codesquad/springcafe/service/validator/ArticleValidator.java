package codesquad.springcafe.service.validator;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.domain.repository.ArticleRepository;
import codesquad.springcafe.exception.InvalidAccessException;
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
        String actual = articleRepository.getById(Long.parseLong(articleId)).getWriter();
        String expected = writer.getUserId();
        if (!actual.equals(expected)) {
            throw new InvalidAccessException("접근 권한이 없습니다");
        }
    }
}