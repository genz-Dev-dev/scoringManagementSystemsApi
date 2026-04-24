package com.kh.rupp_dev.boukryuniversity.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
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
    @Size(min = 2, max = 30, message = "Last name must be between 2 - 30 characters.")
    private String enLastName;

    @NotBlank(message = "Gender is required.")
    private String gender;

    @NotBlank(message = "Date of birth is required.")
    private String dateOfBirth;

    private String email;

    @JsonAlias("phone")
    private String phoneNumber;

    private StudentAddressRequest address;

}