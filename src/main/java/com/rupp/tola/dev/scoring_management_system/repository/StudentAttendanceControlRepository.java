package com.rupp.tola.dev.scoring_management_system.repository;

import com.rupp.tola.dev.scoring_management_system.entity.StudentAttendanceControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentAttendanceControlRepository extends JpaRepository<StudentAttendanceControl , UUID> {
}
