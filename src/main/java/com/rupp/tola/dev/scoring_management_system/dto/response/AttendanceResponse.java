package com.rupp.tola.dev.scoring_management_system.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceResponse {

    private UUID id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String userId;

    private LocalDateTime workDate;

    private String status;
}
