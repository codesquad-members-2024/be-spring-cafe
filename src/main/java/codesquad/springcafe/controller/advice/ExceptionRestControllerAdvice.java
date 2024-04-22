package codesquad.springcafe.controller.advice;

import codesquad.springcafe.service.exception.DataDeletionException;
import codesquad.springcafe.service.exception.ResourceNotFoundException;
import codesquad.springcafe.service.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionRestControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(ExceptionRestControllerAdvice.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResult> resourceNotFoundHandler(ResourceNotFoundException e) {
        log.error("[ResourceNotFoundException-Rest] {}", e.getMessage());
        ErrorResult errorResult = new ErrorResult(HttpStatus.NOT_FOUND.value(), e.getMessage());

        return new ResponseEntity<>(errorResult, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResult> unauthorizedHandler(UnauthorizedException e) {
        log.error("[UnauthorizedException-Rest] {}" , e.getMessage());
        ErrorResult errorResult = new ErrorResult(HttpStatus.FORBIDDEN.value(), e.getMessage());

        return new ResponseEntity<>(errorResult, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DataDeletionException.class)
    public ResponseEntity<ErrorResult> dataDeletionHandler(DataDeletionException e) {
        log.error("[DataDeletionException-Rest] {}", e.getMessage());
        ErrorResult errorResult = new ErrorResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());

        return new ResponseEntity<>(errorResult, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
