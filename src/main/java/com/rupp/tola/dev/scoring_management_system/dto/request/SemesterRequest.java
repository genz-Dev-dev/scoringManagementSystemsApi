package com.rupp.tola.dev.scoring_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SemesterRequest {

    @NotNull(message = "Semester No is required.")
    private int semesterNo;

    @NotBlank(message = "Semester start at is required.")
    private String startAt;

    @NotBlank(message = "Semester end at is required.")
    private String endAt;

}
