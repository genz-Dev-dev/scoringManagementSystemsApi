package com.rupp.tola.dev.scoring_management_system.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentRequest {

    @NotBlank( message = "Student Code is required.")
    private String studentCode;

    @NotBlank(message = "Firs name is required.")
    @Size(min = 10, max = 30, message = "First name must be between 10 - 30 characters.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(min = 10, max = 30, message = "Last name must be between 10 - 30 characters.")
    private String lastName;

    private UUID classesId;

    private boolean status;
}
