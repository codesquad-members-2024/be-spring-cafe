package codesquad.springcafe.controller;

import codesquad.springcafe.exception.db.ArticleNotFoundException;
import codesquad.springcafe.exception.db.UserNotFoundException;
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
        return "error/form";
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleArticleNotFoundException(ArticleNotFoundException e, Model model) {
        logger.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        model.addAttribute("errorMsg", e.getMessage());
        return "error/form";
    }

    @ExceptionHandler(ServletException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleServletException(ServletException e, Model model) {
        logger.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        model.addAttribute("errorMsg", "잘못된 페이지 접근입니다.");
        return "error/form";
    }
}
