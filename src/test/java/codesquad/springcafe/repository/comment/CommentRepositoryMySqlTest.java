package codesquad.springcafe.repository.comment;

import static org.assertj.core.api.Assertions.assertThat;

import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.domain.comment.Comment;
import codesquad.springcafe.repository.article.ArticleRepository;
import codesquad.springcafe.util.Page;
import codesquad.springcafe.util.PageRequest;
import codesquad.springcafe.util.Sort;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CommentRepositoryMySqlTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        // repository 초기화
        articleRepository.clear();
        commentRepository.clear();

        // 게시글 추가
        Article article = new Article();
        article.setTitle("test title");
        article.setContents("test contents");
        article.setCreatedBy("testuser");
        article.setCreatedAt(LocalDateTime.now());

        articleRepository.save(article);

        // comment 300개 추가
        for (int i = 0; i < 300; i++) {
            Comment comment = createComment(i);
            commentRepository.save(comment);
        }
    }

    @DisplayName("0번 페이지 요청 시 요청 사이즈가 15 이면 15개의 댓글을 가져올 수 있다")
    @Test
    void findAllByArticleId() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 15, Sort.sorted());

        // when
        Page<Comment> page = commentRepository.findAllByArticleId(1L, pageRequest);

        // then
        assertThat(page.getPageNumber()).isEqualTo(0);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.isLast()).isFalse();
        assertThat(page.hasNext()).isTrue();
        assertThat(page.getNumberOfElements()).isEqualTo(15);
        assertThat(page.getTotalElements()).isEqualTo(300);
        assertThat(page.getTotalPages()).isEqualTo(20);
    }

    @DisplayName("0번 페이지 요청 시 요청 사이즈가 15 이면 15개의 게시글을 가져올 수 있다")
    @Test
    void findAll() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 15, Sort.sorted());

        // when
        Page<Article> page = articleRepository.findAll(pageRequest);

        // then
        assertThat(page.getPageNumber()).isEqualTo(0);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.isLast()).isFalse();
        assertThat(page.hasNext()).isTrue();
        assertThat(page.getNumberOfElements()).isEqualTo(15);
        assertThat(page.getTotalElements()).isEqualTo(300);
        assertThat(page.getTotalPages()).isEqualTo(20);
    }

    private Comment createComment(int i) {
        Comment comment = new Comment();
        comment.setArticleId(1);
        comment.setContent("test" + i);
        comment.setCreatedBy("testuser");
        comment.setCreatedAt(LocalDateTime.now());
        return comment;
    }
}