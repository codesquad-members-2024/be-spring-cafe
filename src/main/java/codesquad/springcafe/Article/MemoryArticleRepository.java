package codesquad.springcafe.Article;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryArticleRepository implements ArticleRepository{
    private static final List<Article> articles = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(MemoryArticleRepository.class);

    public Article addArticle(Article article) {
        articles.add(article);
        logger.info("글 추가: {}", article);
        return article;
    }

    public List<Article> findAll() {
        logger.info("전체 게시글 조회");
        return articles;
    }

    public Optional<Article> findByIndex(int number) {
        int index = number - 1;
        try {
            Article article = articles.get(index);
            logger.info("게시글 조회: index = {}", index);
            return Optional.of(article);
        } catch (IndexOutOfBoundsException e) {
            logger.warn("게시글 조회 실패: 유효하지 않은 index = {}", index);
            return Optional.empty();
        }
    }

    public int articleSize() {
        return articles.size();
    }
    public void clear() {articles.clear();}
}
