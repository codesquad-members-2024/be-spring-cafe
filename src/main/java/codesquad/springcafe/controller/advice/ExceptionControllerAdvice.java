package codesquad.springcafe.controller.advice;

import codesquad.springcafe.controller.article.ArticleController;
import codesquad.springcafe.controller.comment.CommentController;
import codesquad.springcafe.controller.member.MemberController;
import codesquad.springcafe.service.exception.DataDeletionException;
import codesquad.springcafe.service.exception.ResourceNotFoundException;
import codesquad.springcafe.service.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(assignableTypes = {MemberController.class, CommentController.class, ArticleController.class})
public class ExceptionControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public String resourceNotFoundHandler(ResourceNotFoundException e) {
        log.error("[ResourceNotFoundException] {}", e.getMessage());
        return "error/404";
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UnauthorizedException.class)
    public String unauthorizedHandler(UnauthorizedException e) {
        log.error("[UnauthorizedException] {}" , e.getMessage());
        return "error/403";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataDeletionException.class)
    public String dataDeletionHandler(DataDeletionException e) {
        log.error("[DataDeletionException] {}", e.getMessage());
        return "error/delete-failed";
    }
}
