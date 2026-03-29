package com.rupp.tola.dev.scoring_management_system.repository;

import java.util.Optional;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID>, JpaSpecificationExecutor<Student> {

	Optional<Student> findByClazzId(UUID id);

	List<Student> findAllByClazzId(UUID classId);

	Page<Student> findByStatus(Boolean status, Pageable pageable);

	int countByStatus(Boolean status);

	int countByGender(String gender);

	@Query(value = "SELECT nextval('student_code_seq')", nativeQuery = true)
	Long getNextSequence();
}
