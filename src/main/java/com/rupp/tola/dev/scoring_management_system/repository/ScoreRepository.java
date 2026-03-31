package com.rupp.tola.dev.scoring_management_system.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.dto.response.ScoreResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Student;
import com.rupp.tola.dev.scoring_management_system.entity.composite.CourseId;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rupp.tola.dev.scoring_management_system.entity.Score;

@Repository
public interface ScoreRepository extends JpaRepository<Score, UUID> {

    List<Score> findByStudent_Id(UUID studentId);

    List<Score> findByCourse_CourseId(CourseId courseId);

    Optional<Score> findByStudent_IdAndCourse_CourseId(UUID studentId, CourseId courseId);

    boolean existsByStudent_IdAndCourse_CourseId(UUID studentId, CourseId courseId);

    UUID student(Student student);
}
