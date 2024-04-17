package codesquad.springcafe.exception;

import jakarta.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);


    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFoundException(UserNotFoundException e, Model model) {
        logger.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        model.addAttribute("errorMsg", e.getMessage());
        return "error/404page";
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleArticleNotFoundException(ArticleNotFoundException e, Model model) {
        logger.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        model.addAttribute("errorMsg", e.getMessage());
        return "error/404page";
    }

    @ExceptionHandler(ServletException.class)
    public String showMainPage(ServletException e, Model model) {
        logger.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        String errorMsg = "잘못된 접근입니다.";
        model.addAttribute("errorMsg", errorMsg);
        return "error/404page";
    }
}
