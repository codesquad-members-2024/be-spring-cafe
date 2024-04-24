package codesquad.springcafe.repository;

import codesquad.springcafe.domain.Post;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepositoryJDBC implements PostRepository {

    private final JdbcTemplate jdbcTemplate;
    public PostRepositoryJDBC(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Post post) {
        // SimpleJdbcInsert 객체를 생성하고 JdbcTemplate을 사용하여 데이터베이스에 삽입할 테이블을 지정합니다.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        // 삽입할 테이블을 지정합니다.
        jdbcInsert.withTableName("posts").usingGeneratedKeyColumns("id");
        // 삽입할 데이터를 맵 형태로 변환하여 설정합니다.
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", post.getTitle());
        parameters.put("content", post.getContent());
        parameters.put("createdAt", post.getCreatedAt());
        // 데이터를 삽입합니다.
        jdbcInsert.execute(parameters);
    }

    @Override
    public Optional<Post> findById(Long postId) {
        String sql = "SELECT * FROM posts WHERE id = ?";
        List<Post> result = jdbcTemplate.query(sql, postRowMapper(), postId);
        return result.stream().findFirst();
    }

    @Override
    public List<Post> findAll() {
        String sql = "SELECT * FROM posts";
        return jdbcTemplate.query(sql, postRowMapper());
    }

    private RowMapper<Post> postRowMapper() {
        return (rs, rowNum) -> {
            Post post = new Post();
            post.setId(rs.getLong("id"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            post.setCreatedAt(rs.getDate("createdAt").toLocalDate());
            return post;
        };
    }

}