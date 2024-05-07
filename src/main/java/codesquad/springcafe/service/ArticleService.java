package codesquad.springcafe.service;

import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.domain.article.ArticleRepository;
import codesquad.springcafe.domain.user.User;
import codesquad.springcafe.web.dto.ArticleCreateDto;
import codesquad.springcafe.web.dto.ArticleUpdateDto;
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

    public void updateArticle(Long id, ArticleUpdateDto articleUpdateDto) {
        articleRepository.update(id, articleUpdateDto);
    }

    public void deleteArticle(Long id) {
        articleRepository.delete(id);
    }

    public Article findById(Long id) {
        return articleRepository.findById(id).get();
    }

    public List<Article> getArticles() {
        return articleRepository.findAll();
    }
}
