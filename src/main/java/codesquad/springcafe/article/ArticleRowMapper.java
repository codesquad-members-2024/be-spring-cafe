package codesquad.springcafe.article;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ArticleRowMapper implements RowMapper<Article> {

    @Override
    public Article mapRow(ResultSet resultSet, int i) throws SQLException {
        return new ArticleBuilder()
            .articleId(resultSet.getLong("articleId"))
            .author(resultSet.getString("author"))
            .title(resultSet.getString("title"))
            .contents(resultSet.getString("contents"))
            .userId(resultSet.getString("userId"))
            .deleted(resultSet.getBoolean("deleted"))
            .build();
    }

}
