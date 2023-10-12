package by.evlashkina.crypto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ErrorHandlerController {

    @ExceptionHandler(value = {ApiException.class})
    protected ResponseEntity<ApiErrorResponse> handleEntityNotFoundException(
            ApiException ex, WebRequest request) {
        return new ResponseEntity<>(ApiErrorResponse.builder()
                .message(ex.getMessage())
                .build(),
                ex.getHttpStatus());
    }
}