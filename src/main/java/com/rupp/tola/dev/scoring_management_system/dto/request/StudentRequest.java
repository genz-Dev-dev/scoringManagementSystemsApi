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

    private UUID id;

    private UUID classId;

    @NotBlank(message = "Khmer First name is required.")
    @Size(min = 2, max = 30, message = "First name must be between 10 - 30 characters.")
    private String khFirstName;

    @NotBlank(message = "Khmer Last name is required.")
    @Size(min = 2, max = 30, message = "Last name must be between 10 - 30 characters.")
    private String khLastName;

    @NotBlank(message = "English First name is required.")
    @Size(min = 2, max = 30, message = "First name must be between 10 - 30 characters.")
    private String enFirstName;

    @NotBlank(message = "English name is required.")
    @Size(min = 2, max = 30, message = "Last name must be between 10 - 30 characters.")
    private String enLastName;

    @Size(max = 1)
    private String gender;

    private String dateOfBirth;

    private String email;

    private String phoneNumber;

    private StudentAddressRequest address;

}
