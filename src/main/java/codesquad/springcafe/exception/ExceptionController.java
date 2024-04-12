package codesquad.springcafe.exception;

import codesquad.springcafe.users.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public class ExceptionController {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException e) {
        logger.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        return "/error/404page";
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public String handleArticleNotFoundException(ArticleNotFoundException e) {
        logger.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        return "/error/404page";
    }
}
