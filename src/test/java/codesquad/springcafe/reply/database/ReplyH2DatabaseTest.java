package codesquad.springcafe.reply.database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import codesquad.springcafe.reply.Reply;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

class ReplyH2DatabaseTest {

    private JdbcTemplate jdbcTemplate;

    private ReplyH2Database replyH2Database;

    @BeforeEach
    public void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        replyH2Database = new ReplyH2Database(jdbcTemplate);
    }

    @Test
    void testFindById() {
        Reply expectedReply = new Reply(789L, "Test reply", "hi", 123L,
            Timestamp.valueOf(LocalDateTime.now()), false);

        when(jdbcTemplate.queryForObject("SELECT * FROM reply WHERE replyId = ?",
            new ReplyRowMapper(), 789L))
            .thenReturn(expectedReply);

        Reply retrievedReply = replyH2Database.findById(789L);
        assertThat(retrievedReply).isEqualTo(expectedReply);
    }
}