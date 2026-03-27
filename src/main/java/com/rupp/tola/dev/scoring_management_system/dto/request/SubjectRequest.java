package com.rupp.tola.dev.scoring_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectRequest {

    @NotBlank(message = "Department id is required.")
    private String departmentId;

    @NotBlank(message = "Subject name is required.")
    private String name;

    @NotBlank(message = "Subject description is required.")
    private String description;

    @NotBlank(message = "Subject code is required.")
    private String code;
}
