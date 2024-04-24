package codesquad.springcafe.repository.article;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.dto.ArticleDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryArticleRepository implements ArticleRepository {
    private static final Logger logger = LoggerFactory.getLogger(MemoryArticleRepository.class);
    private static final List<Article> articles = new ArrayList<>();

    @Override
    public void createArticle(ArticleDto articleDto) {
        Article article = articleDto.toEntity();
        long id = articles.size() + 1;
        article.setId(id);
        articles.add(article);
        logger.debug("게시글 생성: {}", article.toDto());
    }

    @Override
    public List<Article> findAllArticles() {
        return articles;
    }

    @Override
    public Optional<Article> findById(long id) {
        return findAllArticles().stream().filter(article -> article.getId()==id).findFirst();
    }
}