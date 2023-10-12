package by.evlashkina.crypto.exception;

import org.springframework.http.HttpStatus;

public class UserException extends ApiException {

    public UserException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}