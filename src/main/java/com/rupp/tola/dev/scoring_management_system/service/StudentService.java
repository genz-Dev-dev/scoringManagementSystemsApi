package com.rupp.tola.dev.scoring_management_system.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.dto.request.StudentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface StudentService {

	StudentResponse create(StudentRequest request);

	StudentResponse getById(UUID uuid);

	Page<StudentResponse> getAll(Map<String, String> param);

	Optional<StudentResponse> findByClassesId(UUID id);

	Page<StudentResponse> findByStatus(boolean status, Pageable pageable);

	StudentResponse update(UUID uuid, StudentRequest request);

	void delete(UUID uuid);

	Page<StudentResponse> getByStatusPagination(Map<String, String> param, Boolean status);

}
