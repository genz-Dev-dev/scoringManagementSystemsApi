package com.rupp.tola.dev.scoring_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SemesterRequest {

    @NotBlank(message = "Semester name is required.")
    private String name;

    @NotBlank(message = "Semester start date is required.")
    private String startDate;

    @NotBlank(message = "Semester end date is required.")
    private String endDate;

    @NotBlank(message = "Semester description is required.")
    private String description;

}
