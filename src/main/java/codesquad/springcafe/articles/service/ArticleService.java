package codesquad.springcafe.articles.service;

import codesquad.springcafe.articles.repository.ArticleRepository;
import codesquad.springcafe.exception.ArticleNotFoundException;
import model.article.Article;
import model.article.dto.ArticleCreateDto;
import model.article.dto.ArticleContentDto;
import model.article.dto.ArticlePreviewDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ArticleService {
    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void createArticle(ArticleCreateDto articleCreateDto) {
        Article article = new Article(articleCreateDto.getUserId(), articleCreateDto.getTitle(), articleCreateDto.getContent());
        logger.debug("Article Created : {}", article);
        articleRepository.createArticle(article);
    }

    public ArrayList<ArticlePreviewDto> getAllArticles() {
        Optional<ArrayList<ArticlePreviewDto>> articles = articleRepository.getAllArticles();
        return articles.orElseThrow(() -> new ArticleNotFoundException("게시글을 찾을 수 없습니다."));
    }

    public ArticleContentDto findArticleById(int articleId) {
        Optional<ArticleContentDto> articleContent = articleRepository.findArticleById(articleId);
        return articleContent.orElseThrow(() -> new ArticleNotFoundException("게시글을 찾을 수 없습니다."));
    }

}
