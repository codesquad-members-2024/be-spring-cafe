package codesquad.springcafe.exception;

import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ResponseExceptionHandler {
    @ExceptionHandler(value = {IndexOutOfBoundsException.class, NoSuchElementException.class})
    private ModelAndView handleException() {
        ModelAndView modelAndView = new ModelAndView("error/noArticle");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}