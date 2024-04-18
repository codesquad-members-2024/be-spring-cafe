package codesquad.springcafe.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
}
