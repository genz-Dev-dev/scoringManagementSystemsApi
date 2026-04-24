package com.kh.rupp_dev.boukryuniversity.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse<T> {
    private boolean success;
    private int status;
    private String message;
    private T error;
    private LocalDate localDate;

    public static <T> ErrorResponse<T> error(HttpStatus status , String message , T error) {
        return builderErrorResponse(status, message, error);
    }

    public static <T> ErrorResponse<T> error(HttpStatus status , String message) {
        return builderErrorResponse(status, message, null);
    }

    public static <T> ErrorResponse<T> error(String message) {
        return builderErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message, null);
    }

    private static <T> ErrorResponse<T> builderErrorResponse(HttpStatus status, String message , T error) {
        return ErrorResponse
                .<T>builder()
                .success(false)
                .status(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .error(null)
                .localDate(LocalDate.now())
                .build();
    }
}
