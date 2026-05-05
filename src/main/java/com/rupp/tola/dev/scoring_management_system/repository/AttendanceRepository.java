package com.rupp.tola.dev.scoring_management_system.repository;

import com.rupp.tola.dev.scoring_management_system.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance , UUID> {

    Optional<Attendance> findByUserIdAndWorkDate(UUID userId, LocalDate workDate);

    boolean existsByUserIdAndWorkDate(UUID userId, LocalDate workDate);

}
