package codesquad.springcafe.articles.repository;

import model.article.Article;
import model.article.dto.ArticleContentDto;
import model.article.dto.ArticlePreviewDto;
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
import java.util.Optional;
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
    public void createArticle(Article article) {
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
        logger.debug("Article Title : '{}' Updated At H2 Database", article.getTitle());
    }

    @Override
    public Optional<ArrayList<ArticlePreviewDto>> getAllArticles() {
        ArrayList<ArticlePreviewDto> articlePreviews = new ArrayList<>();
        /*
         * 모든 게시물들을 출력하는 데에 필요한 정보들
         * 1) title
         * 2) userId
         * 3) creationDate
         * 4) 조회수 [아직 미구현]
         * */
        String sql = "SELECT ARTICLEID, USERID, TITLE, CREATIONDATE FROM ARTICLES";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                long articleId = rs.getInt("articleId");
                String userId = rs.getString("userId");
                String title = rs.getString("title");
                String creationDate = rs.getString("creationDate");
                ArticlePreviewDto articlePreviewDTO = new ArticlePreviewDto(articleId, userId, title, creationDate);
                articlePreviews.add(articlePreviewDTO);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return Optional.empty(); // 예외 발생 시 Optional.empty()를 반환
        }
        Collections.reverse(articlePreviews);
        return Optional.of(articlePreviews); // Optional에 articlePreviews를 감싸서 반환
    }

    @Override
    public Optional<ArticleContentDto> findArticleById(int articleId) {
        String sql = "SELECT USERID, TITLE, CONTENT, CREATIONDATE FROM ARTICLES WHERE articleId = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, articleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String userId = rs.getString("userId");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    String creationDate = rs.getString("creationDate");
                    ArticleContentDto articleContent = new ArticleContentDto(userId, title, content, creationDate);
                    return Optional.of(articleContent);
                }
                return Optional.empty(); // Article을 찾지 못한 경우
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return Optional.empty(); // 예외 발생 시
        }
    }

}
