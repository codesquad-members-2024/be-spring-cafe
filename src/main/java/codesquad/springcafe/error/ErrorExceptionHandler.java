package codesquad.springcafe.error;

import codesquad.springcafe.error.exception.ArticleNotFoundException;
import codesquad.springcafe.error.exception.ReplyNotFoundException;
import codesquad.springcafe.error.exception.UserNotFoundException;
import codesquad.springcafe.error.exception.AccessDeniedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ErrorExceptionHandler.class);

    @ExceptionHandler(value = {AccessDeniedException.class, ArticleNotFoundException.class,
            ReplyNotFoundException.class,
            UserNotFoundException.class})
    public String handleException(Exception e, Model model) {
        logger.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "error/page";
    }
}