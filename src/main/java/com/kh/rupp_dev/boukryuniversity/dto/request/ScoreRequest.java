package com.kh.rupp_dev.boukryuniversity.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreRequest {

    @NotNull(message = "Semester ID is required")
    private UUID semesterId;

    @NotNull(message = "Subject ID is required")
    private UUID subjectId;

    @NotNull(message = "Student ID is required")
    private UUID studentId;

    @Min(value = 0, message = "Score must be at least 0")
    @Max(value = 100, message = "Score must not exceed 100")
    private double score;

    private String grade;

    private boolean status;

}
