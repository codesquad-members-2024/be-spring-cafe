package codesquad.springcafe.service;

import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.domain.article.ArticleRepository;
import codesquad.springcafe.domain.user.User;
import codesquad.springcafe.web.dto.ArticleCreateDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void saveArticle(User loginUser, ArticleCreateDto articleCreateDto) {
        articleRepository.save(new Article(
                loginUser.getId(),
                loginUser.getUserId(),
                articleCreateDto.getTitle(),
                articleCreateDto.getContents(),
                articleCreateDto.getCurrentTime()
        ));
    }

    public Article findById(Long id) {
        return articleRepository.findById(id).get();
    }

    public List<Article> getArticles() {
        return articleRepository.findAll();
    }
}
