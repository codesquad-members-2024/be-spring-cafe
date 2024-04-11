package codesquad.springcafe.articles.repository;

import codesquad.springcafe.exception.ArticleNotFoundException;
import model.Article;
import model.ArticleData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

@Primary
@Repository
public class H2ArticleRepository implements ArticleRepository {
    private static final Logger logger = LoggerFactory.getLogger(H2ArticleRepository.class);

    private final DataSource dataSource;

    @Autowired
    public H2ArticleRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void createArticle(ArticleData articleData) {
        String userId = articleData.getUserId();
        String title = articleData.getTitle();
        String content = articleData.getContent();

        Article article = new Article(userId, title, content);
        logger.debug("Article Created : {}", article);

        String sql = "INSERT INTO ARTICLES (USERID, TITLE, CONTENT, CREATIONDATE) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, article.getUserId());
            pstmt.setString(2, article.getTitle());
            pstmt.setString(3, article.getContent());
            pstmt.setString(4, article.getCreationDate().toString()); // LocalDate를 문자열로 변환하여 설정
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public ArrayList<Article> getAllArticles() {
        ArrayList<Article> articles = new ArrayList<>();
        String sql = "SELECT * FROM ARTICLES";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int articleId = rs.getInt("articleId");
                String userId = rs.getString("userId");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String creationDate = rs.getString("creationDate");
                Article article = new Article(articleId, userId, title, content, creationDate);
                articles.add(article);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        Collections.reverse(articles);
        return articles;
    }

    @Override
    public Article findArticleById(int articleId) {
        Article article = null;
        String sql = "SELECT * FROM ARTICLES WHERE articleId = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, articleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String userId = rs.getString("userId");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    String creationDate = rs.getString("creationDate");
                    article = new Article(articleId, userId, title, content, creationDate);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        if (article == null) {
            throw new ArticleNotFoundException("존재하는 게시글이 없습니다 Article ID: " + articleId);
        }

        return article;
    }
}
