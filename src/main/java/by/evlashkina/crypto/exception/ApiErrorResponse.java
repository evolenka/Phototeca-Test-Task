package by.evlashkina.crypto.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiErrorResponse {
    private String message;
}