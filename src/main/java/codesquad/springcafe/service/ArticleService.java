package codesquad.springcafe.service;

import codesquad.springcafe.controller.ArticleForm;
import codesquad.springcafe.domain.Article;
import codesquad.springcafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Long save(ArticleForm articleForm) {
        Article article = new Article(articleForm.getWriter(),
                                        articleForm.getTitle(),
                                        articleForm.getContents(),
                                        LocalDateTime.now());
        return articleRepository.save(article);
    }

    public List<Article> findArticle() {
        return articleRepository.findAllArticle();
    }

    public Optional<Article> findOne(Long articleId) {
        return articleRepository.findById(articleId);
    }

}
