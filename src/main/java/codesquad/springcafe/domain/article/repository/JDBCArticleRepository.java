package codesquad.springcafe.domain.article.repository;

import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.domain.article.DTO.ArticlePostReq;
import codesquad.springcafe.domain.user.DTO.SimpleUserInfo;
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

import static codesquad.springcafe.domain.article.repository.ArticleConsts.*;
import static java.sql.Types.INTEGER;
import static java.sql.Types.VARCHAR;

@Repository
@Primary
public class JDBCArticleRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JDBCArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void add(ArticlePostReq articlePostReq, SimpleUserInfo simpleUserInfo) throws IllegalArgumentException {
        String ADD = "INSERT INTO ARTICLE (TITLE, CONTENT, CREATEDAT, AUTHORID, POINT) VALUES ( ?, ?, ?, ?, ?);";

        Object[] args = new Object[]{
                articlePostReq.title(),
                articlePostReq.content(),
                Timestamp.valueOf(LocalDateTime.now()),
                simpleUserInfo.id(),
                DEFAULT_POINT};

        jdbcTemplate.update(ADD, args);
    }

    @Override
    public void delete(int id) {
        String DELETE = "UPDATE ARTICLE SET STATUS = ? WHERE ID = ? ;";
        Object[] args = new Object[]{CLOSE, id};

        jdbcTemplate.update(DELETE, args);
    }

    @Override
    public Article findById(int id) {
        String FIND_BY_ID = "SELECT * FROM ARTICLE WHERE ID = ? AND STATUS = ?;";

        Object[] args = new Object[]{id, OPEN};
        int[] pramTypes = new int[]{INTEGER, VARCHAR};
        try {
            return jdbcTemplate.query(FIND_BY_ID, args, pramTypes, articleRowMapper()).get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public List<Article> findAll() {
        String FIND_ALL = "SELECT * FROM ARTICLE WHERE STATUS = ? ORDER BY CREATEDAT DESC;";

        Object[] args = new Object[]{OPEN};
        int[] pramTypes = new int[]{VARCHAR};
        return jdbcTemplate.query(FIND_ALL, args, articleRowMapper());
    }

    @Override
    public List<Article> findByUserId(String id) {
        String FIND_BY_USER = "SELECT * FROM ARTICLE WHERE AUTHORID = ? AND STATUS = ?;";

        Object[] args = new Object[]{id, OPEN};
        int[] pramTypes = new int[]{VARCHAR, VARCHAR};
        return jdbcTemplate.query(FIND_BY_USER, args, pramTypes, articleRowMapper());
    }

    @Override
    public void addPoint(int articleId) {
        String ADD_POINT = "UPDATE ARTICLE SET POINT = ARTICLE.POINT + 1 WHERE ID = ?;";

        Object[] args = new Object[]{articleId};
        jdbcTemplate.update(ADD_POINT, args);
    }

    @Override
    public void update(int id, ArticlePostReq articlePostReq) {
        String UPDATE = "UPDATE ARTICLE SET TITLE = ?, CONTENT = ?,  CREATEDAT = ?  WHERE ID = ?;";

        Object[] args = new Object[]{articlePostReq.title(), articlePostReq.content(), Timestamp.valueOf(LocalDateTime.now()), id};
        jdbcTemplate.update(UPDATE, args);
    }

    @Override
    public void deleteAll() {
        String DELETE_ALL = "DELETE FROM ARTICLE";

        jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public List<Article> getArticles(int page) {
        String paging_query = "SELECT * FROM ARTICLE WHERE STATUS = ? ORDER BY CREATEDAT DESC LIMIT ? OFFSET ?";

        int COMMENTS_PER_PAGE = 15;
        int START_INDEX = COMMENTS_PER_PAGE * (page - 1);

        Object[] args = new Object[]{OPEN, COMMENTS_PER_PAGE, START_INDEX};
        int[] pramTypes = new int[]{Types.VARCHAR, Types.INTEGER, Types.INTEGER};
        return jdbcTemplate.query(paging_query, args, pramTypes, articleRowMapper());
    }

    private RowMapper<Article> articleRowMapper() {
        return (resultSet, rowNum) -> new Article(
                resultSet.getInt(ID),
                resultSet.getTimestamp(CREATEDAT),
                new SimpleUserInfo(resultSet.getString(AUTHORID), getUserName(resultSet.getString(AUTHORID))),
                resultSet.getString(TITLE),
                resultSet.getString(CONTENT),
                resultSet.getInt(POINT)
        );
    }

    private String getUserName(String id) {
        String GET_USER_NAME = "SELECT NAME FROM USERS WHERE USERID = ?";

        try {
            Object[] args = new Object[]{id};
            int[] paramTypes = new int[]{VARCHAR};
            return jdbcTemplate.queryForObject(GET_USER_NAME, args, paramTypes, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
