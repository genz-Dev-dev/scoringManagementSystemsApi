package com.rupp.tola.dev.scoring_management_system.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.dto.response.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.rupp.tola.dev.scoring_management_system.entity.Students;

@Repository
public interface StudentsRepository extends JpaRepository<Students, UUID>, JpaSpecificationExecutor<Students> {
	Optional<Students> findByClassesId(UUID id);
	Page<Students> findByStatus(Boolean status, Pageable pageable);
}
