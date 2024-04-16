package codesquad.springcafe.database.comment;

import static org.assertj.core.api.Assertions.assertThat;

import codesquad.springcafe.database.article.ArticleDatabase;
import codesquad.springcafe.database.user.UserDatabase;
import codesquad.springcafe.model.Article;
import codesquad.springcafe.model.Comment;
import codesquad.springcafe.model.User;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CommentDatabaseTest {
    @Autowired
    UserDatabase userDatabase;
    @Autowired
    ArticleDatabase articleDatabase;
    @Autowired
    CommentDatabase commentDatabase;
    Long articleId;

    @BeforeEach
    void setUp() {
        articleDatabase.clear();
        commentDatabase.clear();
        userDatabase.clear();

        User user1 = new User("sangchu@gmail.com", "상추", "123");
        User user2 = new User("baechu@gmail.com", "배추", "123");
        userDatabase.add(user1);
        userDatabase.add(user2);

        Article article = new Article("상추", "제목", "내용");
        articleId = articleDatabase.add(article).getId();
    }

    @Test
    @DisplayName("사용자가 작성한 comment를 데이터베이스에 저장하고 조회할 수 있다.")
    void findById() {

        Comment comment = new Comment("상추", "내용", articleId, LocalDateTime.now());
        Long id = commentDatabase.add(comment).getId();

        Comment result = commentDatabase.findBy(id).get();
        assertThat(result).isEqualTo(comment);
    }

    @Test
    @DisplayName("게시글에 종속되는 모든 Comment를 조회할 수 있다.")
    void findAll() {

        Comment comment1 = new Comment("상추", "내용1", articleId, LocalDateTime.now());
        Comment comment2 = new Comment("배추", "내용2", articleId, LocalDateTime.now());
        commentDatabase.add(comment1);
        commentDatabase.add(comment2);

        List<Comment> result = commentDatabase.findAll(articleId);
        assertThat(result).containsExactly(comment1, comment2);
    }

    @Test
    @DisplayName("특정 id를 가진 코멘트를 제거할 수 있다.")
    void delete() {
        Comment comment = new Comment("상추", "내용1", articleId, LocalDateTime.now());
        Long id = commentDatabase.add(comment).getId();

        assertThat(commentDatabase.findBy(id).get()).isEqualTo(comment);

        commentDatabase.delete(id);
        assertThat(commentDatabase.findBy(id)).isEmpty();
    }
}