package com.kh.rupp_dev.boukryuniversity.repository;

import java.util.Optional;
import java.util.UUID;

import com.kh.rupp_dev.boukryuniversity.entity.Course;
import com.kh.rupp_dev.boukryuniversity.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kh.rupp_dev.boukryuniversity.entity.Score;

@Repository
public interface ScoreRepository extends JpaRepository<Score, UUID> {

    Optional<Score> findByCourseAndStudent(Course course, Student student);

}
