package com.rupp.tola.dev.scoring_management_system.repository;

import com.rupp.tola.dev.scoring_management_system.entity.AttendanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttendanceStaffRequestRepository extends JpaRepository<AttendanceRequest, UUID> {
}
