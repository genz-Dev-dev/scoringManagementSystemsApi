package com.rupp.tola.dev.scoring_management_system.dto.response;

import lombok.Data;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDateTime;


@Data
public class AttendanceStaffResponse {
    private String id;
    private String reason;
    private String requestType;
    private String approvedType;
    private LocalDateTime createdAt;
    private String staffId;
    private LocalDateTime requestDate;
}
