package codesquad.springcafe.domain.question.model;

import codesquad.springcafe.global.rowMapper.SimpleRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Primary
@Repository
public class QuestionRepositoryH2 implements QuestionRepository{
    private final Logger logger = LoggerFactory.getLogger(QuestionRepositoryH2.class);

    private final JdbcTemplate jdbcTemplate;
    private final SimpleRowMapper<Question> questionRowMapper;

    @Autowired
    public QuestionRepositoryH2(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.questionRowMapper = new SimpleRowMapper<>(Question.class);
    }

    @Override
    public Question save(Question question) {
        final String sql = "insert into question(userId, title, content, viewCnt) values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, question.getUserId());
            ps.setString(2, question.getTitle());
            ps.setString(3, question.getContent());
            ps.setInt(4, question.getViewCnt());
            return ps;
        }, keyHolder);

        Long key = (Long) Objects.requireNonNull(keyHolder.getKeys()).get("id");
        question.setId(key);

        logger.info("Question saved successfully! | questionId: {} | query : {}", key, sql);

        return question;
    }

    @Override
    public Optional<Question> findById(Long questionId) {
        final String sql = "select * from question where id = ? and deleted = false";
        List<Question> questions = jdbcTemplate.query(sql, questionRowMapper, questionId);

        if (questions.size() > 1) {
            throw new RuntimeException("게시글을 두 개 이상 조회하고 있습니다.");
        }

        logger.info("Question find by Id | questionId: {} | query : {}", questionId, sql);

        return questions.stream().findFirst();
    }

    @Override
    public void update(Long questionId, Question updateQuestion) {
        final String sql = "UPDATE question SET title = ?, content = ? WHERE id = ?";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, updateQuestion.getTitle());
            ps.setString(2, updateQuestion.getContent());
            ps.setLong(3, questionId);
            return ps;
        });

        logger.info("Question update | questionId: {} | query : {}", questionId, sql);
    }

    @Override
    public void viewCntUp(Long questionId, Question question) {
        final String sql = "UPDATE question SET viewCnt = ? WHERE id = ?";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, question.getViewCnt());
            ps.setLong(2, questionId);
            return ps;
        });

        logger.info("Question view cnt up! | questionId: {} | viewCnt : {} | query : {}", questionId, question.getViewCnt(), sql);
    }

    @Override
    public Collection<Question> findAll() {
        final String sql = "select * from question where deleted = false order by createdAt desc";
        logger.info("Find All Questions | query : {}", sql);
        return jdbcTemplate.query(sql, questionRowMapper);
    }

    @Override
    public void softDeleteById(Long questionId, Question deleteQuestion) {
        final String sql = "UPDATE question SET deleted = ? where id = ?";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBoolean(1, deleteQuestion.getDeleted());
            ps.setLong(2, questionId);
            return ps;
        });
        logger.info("Soft Delete Question | questionId : {} | query : {}", questionId, sql);
    }

    @Override
    public void deleteAll() {
        final String sql = "delete from question";
        logger.info("Delete All Questions | query : {}", sql);
        jdbcTemplate.update(sql);
    }
}
