package com.rupp.tola.dev.scoring_management_system.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class AttendanceStaffRequest {
    private UUID userId;
    private String reason;
    private String requestType;
}
