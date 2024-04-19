package codesquad.springcafe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NoSuchException extends RuntimeException{

    public NoSuchException(String message) {
        super(message);
    }
}
