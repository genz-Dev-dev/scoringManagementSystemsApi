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
public class DepartmentRequest {

    @NotBlank(message = "Department name is required.")
    private String name;

    @NotBlank(message = "Department code is required.")
    private String code;

    @NotBlank(message = "Department description is required.")
    private String description;
}
