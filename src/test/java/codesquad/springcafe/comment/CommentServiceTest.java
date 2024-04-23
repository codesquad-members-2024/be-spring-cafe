package codesquad.springcafe.comment;

import codesquad.springcafe.domain.article.ArticleService;
import codesquad.springcafe.domain.article.DTO.ArticlePostReq;
import codesquad.springcafe.domain.comment.CommentService;
import codesquad.springcafe.domain.comment.DTO.Comment;
import codesquad.springcafe.domain.comment.DTO.CommentPostReq;
import codesquad.springcafe.domain.comment.repository.CommentRepository;
import codesquad.springcafe.domain.user.DTO.SimpleUserInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
public class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Autowired
    ArticleService articleService;

    @DisplayName("댓글은 한 페이지에 작성일 기준으로 15개 보여준다")
    @Test
    void commentPaging() {
        // given
        ArticlePostReq articlePostReq = new ArticlePostReq("1", "1");
        SimpleUserInfo user = new SimpleUserInfo("tester", "테스터");
        articleService.postArticle(articlePostReq,  user);

        CommentPostReq post = new CommentPostReq(1, "no");
        // 댓글 15개 등록
        for (int i = 0; i < 15; i++) {
            commentService.postComment(post, user);
        }

        CommentPostReq post2 = new CommentPostReq(1, "yes");
        commentService.postComment(post2, user);

        // when
        List<Comment> pageOneComments = commentService.findByArticleId(1,1);
        List<Comment> pageTwoComments = commentService.findByArticleId(1,2);

        //then
        Assertions.assertThat(pageOneComments.size()).isEqualTo(15);
        Assertions.assertThat(pageTwoComments.size()).isEqualTo(1);
        Assertions.assertThat(pageTwoComments.get(0).getContent()).isEqualTo("yes");
    }
}
