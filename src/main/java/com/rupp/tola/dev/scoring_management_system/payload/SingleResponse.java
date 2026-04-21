package com.rupp.tola.dev.scoring_management_system.payload;

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
public class SingleResponse<T> {

    private boolean success;
    private int status;
    private String message;
    private T data;
    private LocalDate timestamp;

    public static <T> SingleResponse<T> success(String message) {
        return builderSingleResponse(HttpStatus.OK, message , null);
    }

    public static <T> SingleResponse<T> success(String message , T data) {
        return builderSingleResponse(HttpStatus.OK, message, data);
    }

    public static <T> SingleResponse<T> success(String message , HttpStatus status , T data) {
        return builderSingleResponse(status , message , data);
    }

    public static <T> SingleResponse<T> builderSingleResponse(HttpStatus status, String message , T data) {
        return SingleResponse
                .<T>builder()
                .success(true)
                .status(status.value())
                .message(message)
                .data(data)
                .timestamp(LocalDate.now())
                .build();
    }

}
