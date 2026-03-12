package com.rupp.tola.dev.scoring_management_system.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.rupp.tola.dev.scoring_management_system.entity.Students;


public interface StudentService {
	Students createStudents(Students students);

	Students getById(UUID id);

	Optional<Students> findByClassesId(UUID id);

	List<Students> getStudents();

	Page<Students> getByStatusPagination(Map<String, String> param, Boolean status);

}
