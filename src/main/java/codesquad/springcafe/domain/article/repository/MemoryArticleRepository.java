package codesquad.springcafe.domain.article.repository;

import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.domain.article.DTO.ArticlePostReq;
import codesquad.springcafe.annotation.TestRepository;
import codesquad.springcafe.domain.comment.DTO.Comment;
import codesquad.springcafe.domain.user.DTO.SimpleUserInfo;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static codesquad.springcafe.domain.article.repository.ArticleConsts.DEFAULT_POINT;


@TestRepository
public class MemoryArticleRepository implements ArticleRepository {

    private final Map<Integer, Article> articles = new ConcurrentHashMap<>();
    private int nowIndex = 1;

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
    public List<Article> findByUserId(String userId) {
        return articles.values().stream().filter(a -> a.getAuthor().id().equals(userId)).toList();
    }

    @Override
    public void addPoint(int articleId) {
        if(articles.containsKey(articleId)) articles.get(articleId).addPoint();
    }

    @Override
    public void update(int id, ArticlePostReq articlePostReq) {
        SimpleUserInfo author = articles.remove(id).getAuthor();
        add(articlePostReq, author);
    }

    @Override
    public void delete(int id) {
        articles.remove(id);
    }

    @Override
    public void deleteAll() {
        nowIndex = 1;
        articles.clear();
    }

    @Override
    public List<Article> getArticles(int page) {
        int ARTICLES_PER_PAGE = 15;
        int START_INDEX = ARTICLES_PER_PAGE * (page - 1);
        int LAST_INDEX = ARTICLES_PER_PAGE * page;

        List<Article> all = findAll();
        return all.subList(START_INDEX, Math.min(LAST_INDEX, all.size()));
    }
}
