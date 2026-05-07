package com.rupp.tola.dev.scoring_management_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "tbl_attendance_request")
@Data
@NoArgsConstructor
public class AttendanceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "attendance_request_id")
    private UUID id;

    @Column(name = "reason")
    private String reason;

    @Column(name = "request_date")
    private LocalDateTime requestDate;

    @Column(name = "approved_date")
    private LocalDateTime approvedDate;

    @Column(name = "request_type")
    private String requestType;

    @Column(name = "approved_type")
    private String approvedType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}