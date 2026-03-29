package com.rupp.tola.dev.scoring_management_system.repository;

import java.util.List;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.entity.composite.CourseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rupp.tola.dev.scoring_management_system.entity.Score;

@Repository
public interface ScoreRepository extends JpaRepository<Score, UUID> {
    List<Score> findByStudent_Id(UUID studentId);
    List<Score> findByCourse_CourseId(CourseId courseId);
}
