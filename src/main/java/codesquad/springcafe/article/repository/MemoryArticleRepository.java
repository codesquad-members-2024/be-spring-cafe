package codesquad.springcafe.article.repository;

import codesquad.springcafe.article.Article;
import codesquad.springcafe.article.DTO.ArticlePostReq;
import codesquad.springcafe.user.DTO.SimpleUserInfo;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static codesquad.springcafe.article.repository.ArticleConsts.DEFAULT_POINT;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private final Map<Integer, Article> articles = new ConcurrentHashMap<>();
    private int nowIndex = 1;

    public MemoryArticleRepository() {
        add(new ArticlePostReq(" 테스트용 제목", "테스트용 내용"), new SimpleUserInfo("테스터", "testerId"));
    }

    @Override
    public void add(ArticlePostReq articlePostReq, SimpleUserInfo simpleUserInfo) throws IllegalArgumentException {
        Article article = new Article(
                nowIndex,
                Timestamp.valueOf(LocalDateTime.now()),
                simpleUserInfo,
                articlePostReq.title(),
                articlePostReq.content(),
                DEFAULT_POINT);

        articles.put(nowIndex, article);
        nowIndex++;
    }

    @Override
    public Article findById(int id) {
        return articles.get(id);
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articles.values());
    }

    @Override
    public void addPoint(Article article) {
        article.addPoint();
    }
}
