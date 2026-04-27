package com.rupp.tola.dev.scoring_management_system.repository;

import java.util.List;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.dto.response.ClassResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Class, UUID> {

	boolean existsByNameAndAcademicYearAndGeneration(String name, String academicYear, Integer generation);

	boolean existsByNameAndAcademicYearAndGenerationAndIdNot(String name, String academicYear, Integer generation, UUID id);
 }
