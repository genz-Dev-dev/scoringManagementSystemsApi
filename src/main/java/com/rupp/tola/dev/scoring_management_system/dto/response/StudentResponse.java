package com.rupp.tola.dev.scoring_management_system.dto.response;

import com.rupp.tola.dev.scoring_management_system.entity.Address;
import jakarta.persistence.NamedAttributeNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse {

    @NotBlank( message = "Student ID is required.")
    private UUID id;

    @NotBlank(message = "Student Code is required.")
    private String studentCode;

    @NotBlank(message = "Class ID is required.")
    private UUID classId;

    @NotBlank(message = "Khmer First name is required.")
    @Size(min = 10, max = 30, message = "First name must be between 10 - 30 characters.")
    private String khFirstName;

    @NotBlank(message = "Khmer Last name is required.")
    @Size(min = 10, max = 30, message = "Last name must be between 10 - 30 characters.")
    private String khLastName;

    @NotBlank(message = "English First name is required.")
    @Size(min = 10, max = 30, message = "First name must be between 10 - 30 characters.")
    private String enFirstName;

    @NotBlank(message = "English name is required.")
    @Size(min = 10, max = 30, message = "Last name must be between 10 - 30 characters.")
    private String enLastName;

    @Size(max = 1)
    private String gender;

    private LocalDate dateOfBirth;

    private String email;

    private String phoneNumber;

    private Address address;

    private boolean status;
}
