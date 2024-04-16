package codesquad.springcafe.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final String EXCEPTION = "exception";

    private static final Logger log = getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public String handleNotFoundException(HttpServletRequest req, Model model, NotFoundException exception) {
        logDebug(NOT_FOUND, req);

        model.addAttribute(EXCEPTION, exception);
        return "error";
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(FORBIDDEN)
    public String handleAuthorizationException(HttpServletRequest req, Model model, AuthorizationException exception) {
        logDebug(FORBIDDEN, req);

        model.addAttribute(EXCEPTION, exception);
        return "error";
    }

    private static void logDebug(HttpStatus status, HttpServletRequest req) {
        log.debug(status.getReasonPhrase() +  " : " + req.getRequestURI());
    }
}
