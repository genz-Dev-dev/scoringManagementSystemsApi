package com.rupp.tola.dev.scoring_management_system.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MultipleResponse<T> {
    private boolean success;
    private int status;
    private String message;
    private List<T> content;
    private int number;
    private int size;
    private int totalPage;
    private long totalElement;
    private boolean hastPrevious;
    private boolean hastNext;
    private LocalDate timestamp;

    public static <T> MultipleResponse<T> success(HttpStatus status, String message , Page<T> data) {
        return MultipleResponse.<T>builder()
                .success(true)
                .status(status.value())
                .message(message)
                .content(data.getContent())
                .number(data.getNumber() + 1)
                .size(data.getSize())
                .totalPage(data.getTotalPages())
                .totalElement(data.getTotalElements())
                .hastPrevious(data.hasPrevious())
                .hastNext(data.hasNext())
                .timestamp(LocalDate.now())
                .build();
    }

    public static <T> MultipleResponse<T> success(String message , Page<T> data) {
        return MultipleResponse.<T>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message(message)
                .content(data.getContent())
                .number(data.getNumber() + 1)
                .size(data.getSize())
                .totalPage(data.getTotalPages())
                .totalElement(data.getTotalElements())
                .hastPrevious(data.hasPrevious())
                .hastNext(data.hasNext())
                .timestamp(LocalDate.now())
                .build();
    }
}
