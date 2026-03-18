package com.rupp.tola.dev.scoring_management_system.dto.response;

import jakarta.persistence.NamedAttributeNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.support.SimpleTriggerContext;

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


    @NotBlank(message = "Firs name is required.")
    @Size(min = 10, max = 30, message = "First name must be between 10 - 30 characters.")
    private String firstName;


    @NotBlank(message = "Firs name is required.")
    @Size(min = 10, max = 30, message = "First name must be between 10 - 30 characters.")
    private String lastName;

    private UUID classeId;

    private boolean status;
}
