package codesquad.springcafe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ResponseExceptionHandler {
    @ExceptionHandler(value = {InvalidAccessException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    private ModelAndView invalidHandler() {
        ModelAndView modelAndView = new ModelAndView("error/invalidAccess");
        return modelAndView;
    }
}