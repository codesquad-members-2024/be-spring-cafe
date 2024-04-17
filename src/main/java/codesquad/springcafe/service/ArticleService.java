package codesquad.springcafe.service;

import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.domain.article.ArticleRepository;
import codesquad.springcafe.web.dto.ArticleCreateDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void saveArticle(ArticleCreateDto articleCreateDto) {
        articleRepository.save(new Article(
                articleCreateDto.getWriter(),
                articleCreateDto.getTitle(),
                articleCreateDto.getContents(),
                articleCreateDto.getCurrentTime()
        ));
    }

    public Article findBySequence(int sequence) {
        return articleRepository.findBySequence(sequence);
    }

    public List<Article> getArticles() {
        return articleRepository.findAll();
    }
}
