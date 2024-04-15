package codesquad.springcafe.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 전역적으로 예외를 처리하는 핸들러를 포함합니다.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    //TODO: 다른 예외 핸들러 메서드 추가
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("error/server_error");

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        modelAndView.setStatus(httpStatus);

        modelAndView.addObject("httpStatus", httpStatus.value() + " " + httpStatus.getReasonPhrase());
        modelAndView.addObject("errorMessage", ex.getMessage());

        return modelAndView;
    }
}
