package codesquad.springcafe.controller.comment;

import codesquad.springcafe.controller.argumentresolver.LoginId;
import codesquad.springcafe.domain.comment.Comment;
import codesquad.springcafe.service.comment.CommentManager;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questions/{articleId}/comments")
public class CommentApiController {

    private static final Logger log = LoggerFactory.getLogger(CommentApiController.class);
    private final CommentManager commentManager;

    @Autowired
    public CommentApiController(CommentManager commentManager) {
        this.commentManager = commentManager;
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getCommentList(@LoginId String loginId, @PathVariable("articleId") long articleId) {
        /* 전체 댓글 조회 */
        List<Comment> comments = commentManager.findAllComment(articleId);

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<List<Comment>> publish(@LoginId String loginId, @PathVariable("articleId") long articleId,
                                                 @Validated @RequestBody CommentForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        /* 댓글 데이터 추가 */
        Comment requestComment = createComment(loginId, articleId, form);
        commentManager.publish(requestComment);

        /* 전체 댓글 조회 */
        List<Comment> comments = commentManager.findAllComment(articleId);

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<List<Comment>> delete(@LoginId String loginId, @PathVariable("commentId") long commentId,
                                                @PathVariable("articleId") long articleId) {
        Comment comment = commentManager.findComment(commentId);

        /* 작성자 검증 */
        commentManager.validateAuthor(loginId, comment.getCreatedBy());

        /* 해당 댓글 삭제 */
        commentManager.unpublish(commentId);

        /* 전체 댓글 조회 */
        List<Comment> comments = commentManager.findAllComment(articleId);

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    private Comment createComment(String loginId, long articleId, CommentForm form) {
        Comment comment = new Comment();
        comment.setArticleId(articleId);
        comment.setContent(form.getContent());
        comment.setCreatedBy(loginId);
        comment.setCreatedAt(LocalDateTime.now());
        return comment;
    }
}
