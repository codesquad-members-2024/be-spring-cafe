package codesquad.springcafe.article.repository;

import codesquad.springcafe.article.Article;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryArticleRepository implements ArticleRepository{

    private final Map<Integer , Article> articles = new ConcurrentHashMap<>();
    private int nowIndex = 1;

    public MemoryArticleRepository(){
        add(new Article("테스터" , " 테스트용 제목", "테스트용 내용"));
    }

    @Override
    public void add(Article article) throws IllegalArgumentException {
        article.setId(nowIndex);
        article.setCreatedDateTime(Timestamp.valueOf(LocalDateTime.now()));

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
