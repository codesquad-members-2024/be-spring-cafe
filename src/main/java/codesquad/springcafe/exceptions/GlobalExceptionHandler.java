package codesquad.springcafe.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchException.class)
    public String handleNoUserException(NoSuchException noSuch, Model model) {
        model.addAttribute("error", noSuch.getMessage());

        return "error_page";
    }

    @ExceptionHandler(NotAuthenticationException.class)
    public String handleNoUserException(NotAuthenticationException notAuth, Model model) {
        model.addAttribute("error", notAuth.getMessage());

        return "error_page";
    }

    @ExceptionHandler(CanNotLoginException.class)
    public String handleCanNotLoginException(CanNotLoginException canNotLogin, Model model) {
        model.addAttribute("error", canNotLogin.getMessage());

        return "user/login_failed";
    }

    @ExceptionHandler(JsonProcessingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleJsonProcessException(JsonProcessingException json, Model model) {
        model.addAttribute("error", "잘못된 JSON 요청입니다");

        return "error_page";
    }
}
