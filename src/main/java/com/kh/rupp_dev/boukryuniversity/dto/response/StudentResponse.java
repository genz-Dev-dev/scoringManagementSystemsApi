package com.kh.rupp_dev.boukryuniversity.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StudentResponse {

    private UUID id;

    private String studentCode;

    private String khFirstName;

    private String khLastName;

    private String enFirstName;

    private String enLastName;

    private String gender;

    private LocalDate dateOfBirth;

    private LocalDate enrollmentDate;

    private String email;

    private String phoneNumber;

    private StudentAddressResponse address;

    private boolean status;

    private LocalDate creationAt;

    private LocalDate updatedAt;
}
