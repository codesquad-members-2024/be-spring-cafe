package codesquad.springcafe.article;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ArticleRowMapper implements RowMapper<Article> {

    @Override
    public Article mapRow(ResultSet resultSet, int i) throws SQLException {
        Article article = new Article();
        article.setArticleId(resultSet.getLong("articleId"));
        article.setAuthor(resultSet.getString("author"));
        article.setTitle(resultSet.getString("title"));
        article.setContents(resultSet.getString("contents"));
        return article;
    }

}
