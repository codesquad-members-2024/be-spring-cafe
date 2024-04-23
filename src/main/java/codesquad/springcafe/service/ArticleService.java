package codesquad.springcafe.service;

import codesquad.springcafe.dto.article.ArticleInfoDTO;
import codesquad.springcafe.dto.article.ArticleUpdateDTO;
import codesquad.springcafe.dto.article.UploadDTO;
import codesquad.springcafe.model.Article;
import codesquad.springcafe.repository.article.ArticleRepository;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private Long totalIndex;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
        this.totalIndex = initTotalIndex();
    }

    public ArticleInfoDTO upload(UploadDTO uploadDTO) {
        Article newArticle = uploadDTO.toArticle(++totalIndex);
        articleRepository.save(newArticle);
        return newArticle.toDTO();
    }

    public List<ArticleInfoDTO> findAll() {
        List<Article> articles = articleRepository.getAll();
        return articles.stream()
            .map(Article::toDTO)
            .collect(Collectors.toList());
    }

    public ArticleInfoDTO findByIndex(Long index) {
        Optional<Article> targetArticle = articleRepository.getByIndex(index);
        if (targetArticle.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return targetArticle.get().toDTO();
    }

    private Long initTotalIndex() {
        OptionalLong maxIndex = articleRepository.getAll().stream().mapToLong(Article::getIndex).max();
        if (maxIndex.isEmpty()) {
            return 0L;
        }
        return maxIndex.getAsLong();
    }
}
