package com.rupp.tola.dev.scoring_management_system.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreRequest {

    @NotNull(message = "Student ID is required!")
    private UUID studentId;

    @NotNull(message = "Semester ID is required!")
    private UUID semesterId;

    @NotNull(message = "Subject ID is required!")
    private UUID subjectId;

    @NotNull(message = "Score is required for student.")
    @Min(0)
    @Max(100)
    private BigDecimal score;

    private String grade;

    private boolean status;

}
