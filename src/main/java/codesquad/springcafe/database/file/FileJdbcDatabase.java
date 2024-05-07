package codesquad.springcafe.database.file;

import codesquad.springcafe.model.UploadFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class FileJdbcDatabase implements FileDatabase {
    private final JdbcTemplate jdbcTemplate;

    public FileJdbcDatabase(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UploadFile add(UploadFile uploadFile) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("files").usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("article_id", uploadFile.getArticleId());
        params.put("upload_file_name", uploadFile.getUploadFileName());
        params.put("store_file_name", uploadFile.getStoreFileName());

        Number key = simpleJdbcInsert.executeAndReturnKey(params);
        uploadFile.setId(key.longValue());

        return uploadFile;
    }

    @Override
    public List<UploadFile> findAll(Long articleId) {
        String sql = "SELECT id, article_id, upload_file_name, store_file_name FROM files WHERE article_id = ?";
        return jdbcTemplate.query(sql, uploadFileRowMapper(), articleId);
    }

    @Override
    public Optional<UploadFile> findBy(Long id) {
        String sql = "SELECT id, article_id, upload_file_name, store_file_name FROM files WHERE id = ?";
        return jdbcTemplate.query(sql, uploadFileRowMapper(), id)
                .stream()
                .findAny();
    }


    public RowMapper<UploadFile> uploadFileRowMapper() {
        return (rs, rowNum) -> {
            long id = rs.getLong("id");
            long articleId = rs.getLong("article_id");
            String uploadFileName = rs.getString("upload_file_name");
            String storeFileName = rs.getString("store_file_name");

            UploadFile uploadFile = new UploadFile(articleId, uploadFileName, storeFileName);
            uploadFile.setId(id);
            return uploadFile;
        };
    }
}
