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
public class SingleResponse<T> {

    private boolean success;
    private int status;
    private String message;
    private T data;
    private LocalDate localDate;

    public static <T> SingleResponse<T> success(boolean success , String message , HttpStatus status , T data) {
        return SingleResponse
                .<T>builder()
                .success(success)
                .status(status.value())
                .message(message)
                .data(data)
                .localDate(LocalDate.now())
                .build();
    }

    public static <T> SingleResponse<T> success(String message , T data) {
        return SingleResponse.
                <T>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message(message)
                .data(data)
                .localDate(LocalDate.now())
                .build();
    }

}
