package springcafe.user.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springcafe.user.exception.UserNotFoundException;
import springcafe.user.exception.WrongIdPasswordException;
import springcafe.user.exception.WrongPasswordException;
import springcafe.user.exception.WrongWriterException;


@ControllerAdvice
public class ErrorController {

    Logger logger = LoggerFactory.getLogger("ErrorController");

    @ExceptionHandler(WrongIdPasswordException.class)
    public String  handleWrongIdPasswordException(WrongIdPasswordException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "User not found or wrong ID provided");
        return "redirect:/user/login_failed";
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("처리 중 오류가 발생했습니다.");
    }

    @ExceptionHandler(WrongPasswordException.class)
    public String handleWrongPasswordException(WrongPasswordException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("passwordError", e.getMessage());
        return "redirect:/user/update";
    }

    @ExceptionHandler(WrongWriterException.class)
    public String handleWrongwriterException(WrongWriterException e){

        return "qna/error";
    }
}
