package codesquad.springcafe.global.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 전역적으로 예외를 처리하는 핸들러를 포함합니다.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //TODO: 다른 예외 핸들러 메서드 추가
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        logger.error("[INTERNAL_SERVER_ERROR] exception : {} | message : {} | cause : {}",
                ex.getClass(), ex.getMessage(), getExceptionStackTrace(ex));

        ModelAndView modelAndView = new ModelAndView("error/server_error");

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        modelAndView.setStatus(httpStatus);

        modelAndView.addObject("httpStatus", httpStatus.value() + " " + httpStatus.getReasonPhrase());
        modelAndView.addObject("errorMessage", ex.getMessage());

        return modelAndView;
    }

    private String getExceptionStackTrace(Exception ex) {
        StringWriter stringWriter = new StringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        return String.valueOf(stringWriter);
    }
}
