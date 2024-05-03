package codesquad.springcafe.post.database;

import codesquad.springcafe.post.Post;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PostJdbcRepository implements PostRepository {

    JdbcTemplate jdbcTemplate;

    public PostJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Post> findAll() {
        String sql = "SELECT * FROM posts";
        return jdbcTemplate.query(sql,postRowMapper());
    }

    @Override
    public void save(String userId, Post post) {
        post.setAuthor(userId);
        post.setDateTime(LocalDateTime.now());

        Timestamp timestamp = Timestamp.valueOf(post.getDateTime());
        String sql = "INSERT INTO posts (AUTHOR, TITLE, CONTENT, CREATION_TIME) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, post.getAuthor(), post.getTitle(), post.getContent(), timestamp);
    }

    @Override
    public Post findById(Long id) {
        String sql = "SELECT * FROM posts WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, postRowMapper(), id);
        }catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void updatePost(Post post, long id) {
        post.setDateTime(LocalDateTime.now());
        String sql = "UPDATE POSTS SET TITLE = ?, CONTENT = ?, CREATION_TIME = ? WHERE ID = ?";
        jdbcTemplate.update(sql, post.getTitle(), post.getContent(), post.getDateTime(), id);
    }

    @Override
    public void deletePost(Long id) {
        String sql = "DELETE FROM POSTS WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


    private RowMapper<Post> postRowMapper() {
        return (rs, rowNum) -> new Post(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("content"),
                rs.getTimestamp("creation_time").toLocalDateTime()
        );
    }
}
