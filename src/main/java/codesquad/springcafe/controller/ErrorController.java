package codesquad.springcafe.controller;

import codesquad.springcafe.exception.ArticleNotFountException;
import codesquad.springcafe.exception.ReplyNotFound;
import codesquad.springcafe.exception.UnauthorizedAccessException;
import codesquad.springcafe.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(value = {UserNotFoundException.class, ArticleNotFountException.class, UnauthorizedAccessException.class, ReplyNotFound.class})
    public ModelAndView handleException(Exception e) {
        ModelAndView model = new ModelAndView("errors/error");
        model.addObject("errorMessage", e.getMessage());
        return model;
    }

}
