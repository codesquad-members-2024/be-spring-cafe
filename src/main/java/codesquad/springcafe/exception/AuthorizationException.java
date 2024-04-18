package codesquad.springcafe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AuthorizationException extends RuntimeException{
    public AuthorizationException(){
        super();
    }

    public AuthorizationException(String message){
        super(message);
    }
}
