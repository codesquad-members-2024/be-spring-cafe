package codesquad.springcafe.db.user;

import codesquad.springcafe.model.user.User;
import codesquad.springcafe.model.user.dto.UserCredentialDto;
import codesquad.springcafe.model.user.dto.UserProfileDto;
import codesquad.springcafe.model.user.dto.UserProfileEditDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;


public class H2UserDatabase implements UserDatabase {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;


    public H2UserDatabase(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public void addUser(User user) {
        simpleJdbcInsert.execute(new BeanPropertySqlParameterSource(user));
    }

    @Override
    public void update(String userId, UserProfileEditDto userProfileEditDto) {
        String sql = "update users set nickname=?, email=? where userid=?";
        jdbcTemplate.update(
                sql,
                userProfileEditDto.getNickname(),
                userProfileEditDto.getEmail(),
                userProfileEditDto.getUserId());
    }

    @Override
    public List<UserProfileDto> getAllUsers() {
        String sql = "SELECT userId, nickname, email, registerTime FROM users";
        return jdbcTemplate.query(
                sql, (rs, rowNum) -> new UserProfileDto(
                        rs.getString("userId"),
                        rs.getString("nickname"),
                        rs.getString("email"),
                        rs.getTimestamp("registerTime").toLocalDateTime())
                );
    }

    @Override
    public Optional<UserProfileDto> findUserByUserId(String userId) {
        String sql = "select * from users where userId = ?";
        return jdbcTemplate.query(
                sql, new Object[]{userId}, rs -> {
                    if(rs.next()){
                        return Optional.of(new UserProfileDto(
                                userId,
                                rs.getString("nickname"),
                                rs.getString("email"),
                                rs.getTimestamp("registerTime").toLocalDateTime()));
                    }
                    return Optional.empty();
                });
    }

    @Override
    public Optional<UserCredentialDto> getUserCredential(String userId) {
        String sql = "select userId, password from users where userId = ?";
        return jdbcTemplate.query(
                sql, new Object[]{userId}, rs -> {
                    if (rs.next()) {
                        return Optional.of(new UserCredentialDto(
                                userId,
                                rs.getString("password")
                        ));
                    }
                    return Optional.empty();
                });
    }

    @Override
    public void clearDatabase() {
        jdbcTemplate.update("delete from users");
    }

    @Override
    public int getTotalUserNumber() {
        return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

    @Override
    public boolean existsByUserId(String userId) {
        return existsByField("userId", userId);
    }

    @Override
    public boolean existsByEmail(String email) {
        return existsByField("email", email);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return existsByField("nickname", nickname);
    }


    private boolean existsByField(String fieldName, String value) {
        String sql = "select count(*) from users where " + fieldName + " = ?";
        Integer result = jdbcTemplate.queryForObject(sql, new Object[] {value}, Integer.class);
        return result != null && result > 0;
    }
}
