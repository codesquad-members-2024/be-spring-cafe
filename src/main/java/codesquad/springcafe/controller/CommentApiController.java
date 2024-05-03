package codesquad.springcafe.controller;

import codesquad.springcafe.form.comment.CommentWriteForm;
import codesquad.springcafe.form.user.LoginUser;
import codesquad.springcafe.model.Comment;
import codesquad.springcafe.service.ArticleService;
import codesquad.springcafe.service.CommentService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/api/articles/detail/{articleId}/comments")
public class CommentApiController {
    private final ArticleService articleService;
    private final CommentService commentService;

    public CommentApiController(ArticleService articleService, CommentService commentService) {
        this.articleService = articleService;
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(@PathVariable Long articleId, @Valid @RequestBody CommentWriteForm form,
                                              @SessionAttribute(LoginController.LOGIN_SESSION_NAME) LoginUser loginUser) {
        articleService.getArticle(articleId); // 아티클이 존재하는지 확인
        Comment comment = commentService.writeComment(articleId, loginUser.getNickname(), form);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable Long articleId, @PathVariable Long id,
                                                 @SessionAttribute(LoginController.LOGIN_SESSION_NAME) LoginUser loginUser) {
        articleService.getArticle(articleId); // 아티클이 존재하는지 확인
        if (isWriter(id, loginUser)) {
            commentService.deleteComment(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping
    public ResponseEntity<List<Comment>> showMoreComments(@PathVariable Long articleId, @RequestParam Long offset) {
        return ResponseEntity.ok(commentService.getCommentsByOffset(articleId, offset));
    }

    private boolean isWriter(Long id, LoginUser loginUser) {
        Comment comment = commentService.getComment(id);
        return loginUser.hasSameNickname(comment.getWriter());
    }
}
