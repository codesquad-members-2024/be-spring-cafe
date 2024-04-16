package codesquad.springcafe.article.repository;

import codesquad.springcafe.article.Article;
import codesquad.springcafe.article.DTO.ArticlePostReq;
import codesquad.springcafe.user.DTO.SimpleUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;

import static codesquad.springcafe.article.repository.ArticleConsts.DEFAULT_POINT;

@Repository
@Primary
public class H2ArticleRepository implements ArticleRepository {

    private final String ADD_SQL = "INSERT INTO ARTICLE (TITLE, CONTENT, CREATEDAT, AUTHORID, POINT) VALUES ( ?, ?, ?, ?, ?);";
    private final String FIND_BY_ID_SQL = "SELECT * FROM ARTICLE WHERE Id = ? AND STATUS = 'OPEN';";
    private final String FIND_BY_USER_SQL = "SELECT * FROM ARTICLE WHERE authorId = ? AND STATUS = 'OPEN'; ";
    private final String FIND_ALL_SQL = "SELECT * FROM article WHERE STATUS = 'OPEN' ORDER BY createdAt DESC;";
    private final String ADD_POINT_SQL = "UPDATE ARTICLE SET point = point + 1 WHERE id = ?;";
    private final String UPDATE_ARTICLE = "UPDATE ARTICLE SET TITLE = ?, CONTENT = ?,  CREATEDAT = ?  WHERE ID = ?;";

    private final String GET_USER_NAME = "SELECT NAME FROM USERS WHERE USERID = ?";
    private final String DELETE_ALL = "DELETE FROM ARTICLE";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public H2ArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void add(ArticlePostReq articlePostReq, SimpleUserInfo simpleUserInfo) throws IllegalArgumentException {
        Object[] args = new Object[]{
                articlePostReq.title(),
                articlePostReq.content(),
                Timestamp.valueOf(LocalDateTime.now()),
                simpleUserInfo.id(),
                DEFAULT_POINT};

        jdbcTemplate.update(ADD_SQL, args);
    }

    @Override
    public void delete(int id) {
        String sql = "UPDATE ARTICLE SET STATUS = 'CLOSE' WHERE ID = ? ;";
        Object[] args = new Object[]{id};

        jdbcTemplate.update(sql, args);
    }

    @Override
    public Article findById(int id) {
        Object[] args = new Object[]{id};
        int[] pramTypes = new int[]{Types.VARCHAR};
        try {
            return jdbcTemplate.query(FIND_BY_ID_SQL, args, pramTypes, articleRowMapper()).get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public List<Article> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, articleRowMapper());
    }

    @Override
    public List<Article> findByUserId(String id) {
        Object[] args = new Object[]{id};
        int[] pramTypes = new int[]{Types.VARCHAR};
        return jdbcTemplate.query(FIND_BY_USER_SQL, args, pramTypes, articleRowMapper());
    }

    @Override
    public void addPoint(int articleId) {
        Object[] args = new Object[]{articleId};
        jdbcTemplate.update(ADD_POINT_SQL, args);
    }

    @Override
    public void update(int id, ArticlePostReq articlePostReq) {
        Object[] args = new Object[]{articlePostReq.title(), articlePostReq.content(), Timestamp.valueOf(LocalDateTime.now()), id};
        jdbcTemplate.update(UPDATE_ARTICLE, args);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    private RowMapper<Article> articleRowMapper() {
        return (resultSet, rowNum) -> new Article(
                resultSet.getInt("id"),
                resultSet.getTimestamp("createdAt"),
                new SimpleUserInfo(resultSet.getString("authorId"), getUserName(resultSet.getString("authorId"))),
                resultSet.getString("title"),
                resultSet.getString("content"),
                resultSet.getInt("point")
        );
    }

    private String getUserName(String id) {
        try {
            Object[] args = new Object[]{id};
            int[] paramTypes = new int[]{Types.VARCHAR};
            return jdbcTemplate.queryForObject(GET_USER_NAME, args, paramTypes, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
