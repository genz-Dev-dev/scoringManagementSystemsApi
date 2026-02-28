package com.rupp.tola.dev.scoring_management_system.data;

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
        return ErrorResponse
                .<T>builder()
                .success(false)
                .status(status.value())
                .message(message)
                .error(error)
                .localDate(LocalDate.now())
                .build();
    }

    public static <T> ErrorResponse<T> error(String message) {
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
