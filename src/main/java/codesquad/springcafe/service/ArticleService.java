package codesquad.springcafe.service;

import codesquad.springcafe.dto.article.ArticleInfoDTO;
import codesquad.springcafe.dto.article.UploadDTO;
import codesquad.springcafe.model.Article;
import codesquad.springcafe.repository.article.ArticleRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private Long index = 0L;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public ArticleInfoDTO upload(UploadDTO uploadDTO) {
        Article newArticle = uploadDTO.toArticle(++index);
        articleRepository.save(newArticle);
        return newArticle.toDTO();
    }

    public List<ArticleInfoDTO> findAllArticles() {
        List<Article> articles = articleRepository.getAll();
        return articles.stream()
            .map(Article::toDTO)
            .collect(Collectors.toList());
    }

    public ArticleInfoDTO findArticleByIndex(Long index) {
        Optional<Article> targetArticle = articleRepository.getByIndex(index);
        if (targetArticle.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return targetArticle.get().toDTO();
    }
}
