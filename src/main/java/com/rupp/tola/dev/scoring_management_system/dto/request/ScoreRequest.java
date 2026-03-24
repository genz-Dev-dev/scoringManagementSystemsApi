package com.rupp.tola.dev.scoring_management_system.dto.request;

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

    @NotNull(message = "Score is required for student.")
    private BigDecimal score;

    @NotNull(message = "Student is required.")
    private UUID studentId;

    @NotNull(message = "Subject is required.")
    private UUID subjectId;

    @NotNull(message = "Semester is required.")
    private UUID semesterId;

}
