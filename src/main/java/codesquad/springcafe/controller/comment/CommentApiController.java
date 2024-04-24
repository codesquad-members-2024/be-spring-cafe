package codesquad.springcafe.controller.comment;

import codesquad.springcafe.controller.argumentresolver.LoginId;
import codesquad.springcafe.domain.comment.Comment;
import codesquad.springcafe.service.comment.CommentManager;
import codesquad.springcafe.util.Page;
import codesquad.springcafe.util.PageRequest;
import codesquad.springcafe.util.Sort;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<Page<Comment>> getCommentList(@LoginId String loginId, @PathVariable("articleId") long articleId,
                                                        @RequestParam(value = "page", defaultValue = "1") int page,
                                                        @RequestParam(value = "size", defaultValue = "15") int size) {
        /* 전체 댓글 조회 */
        PageRequest pageRequest = PageRequest.of(page, size, Sort.sorted());
        Page<Comment> comments = commentManager.findAllComment(articleId, pageRequest);

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Comment> publish(@LoginId String loginId, @PathVariable("articleId") long articleId,
                                           @Validated @RequestBody CommentForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        /* 댓글 데이터 추가 */
        Comment requestComment = createComment(loginId, articleId, form);
        Comment comment = commentManager.publish(requestComment);

        /* 저장된 댓글 반환 */
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<List<Comment>> delete(@LoginId String loginId, @PathVariable("commentId") long commentId,
                                                @PathVariable("articleId") long articleId) {
        Comment comment = commentManager.findComment(commentId);

        /* 작성자 검증 */
        commentManager.validateAuthor(loginId, comment.getCreatedBy());

        /* 해당 댓글 삭제 */
        commentManager.unpublish(commentId);

        return new ResponseEntity<>(HttpStatus.OK);
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
