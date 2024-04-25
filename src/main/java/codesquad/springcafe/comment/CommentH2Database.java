package codesquad.springcafe.comment;

import codesquad.springcafe.article.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Primary
public class CommentH2Database implements CommentDatabase {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public CommentH2Database(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



    @Override
    public void createComment(CommentCreateDTO commentCreateDTO) {
        String sql = "INSERT INTO MAIN.COMMENTS (articleId, writer, content, createdTime) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, commentCreateDTO.getArticleId(),
                commentCreateDTO.getWriter(),
                commentCreateDTO.getContent(),
                commentCreateDTO.getCreatedTime());
    }


}
