package com.rupp.tola.dev.scoring_management_system.repository;

import com.rupp.tola.dev.scoring_management_system.entity.Course;
import com.rupp.tola.dev.scoring_management_system.entity.composite.CourseId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course , CourseId> {
}
