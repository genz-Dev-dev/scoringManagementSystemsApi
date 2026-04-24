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
public class ClassResponse {

    private UUID id;

    private String name;

    private String departmentId;

    private String departmentName;

    private String academicYear;

    private Integer generation;

    private LocalDate creationAt;

    private LocalDate updatedAt;
}
