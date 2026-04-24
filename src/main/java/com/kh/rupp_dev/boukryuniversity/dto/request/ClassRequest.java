package com.kh.rupp_dev.boukryuniversity.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClassRequest {

    @NotBlank(message = "Class name is required.")
    private String name;

    @NotBlank(message = "Department id is required.")
    private String departmentId;

    @NotBlank(message = "Academic year is required.")
    private String academicYear;

    @NotNull(message = "Generation is required.")
    private Integer generation;
}
