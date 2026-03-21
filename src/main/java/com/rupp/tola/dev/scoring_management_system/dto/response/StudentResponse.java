package com.rupp.tola.dev.scoring_management_system.dto.response;

import com.rupp.tola.dev.scoring_management_system.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse {

    private UUID id;

    private String studentCode;

    private UUID classId;

    private String khFirstName;

    private String khLastName;

    private String enFirstName;

    private String enLastName;

    private String gender;

    private LocalDate dateOfBirth;

    private String email;

    private String phoneNumber;

    private Address address;

    private boolean status;
}
