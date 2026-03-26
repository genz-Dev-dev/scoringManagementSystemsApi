package com.rupp.tola.dev.scoring_management_system.service;

import java.util.List;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.dto.request.ImportStudentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.StudentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.StudentResponse;
import com.rupp.tola.dev.scoring_management_system.dto.response.StudentStatisticsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface StudentService {

	@Deprecated
	StudentResponse create(StudentRequest request);

	@Deprecated
	StudentResponse getById(UUID uuid);

	Page<StudentResponse> getAll(Pageable pageable);

	StudentResponse update(UUID uuid, StudentRequest request);

	void delete(UUID uuid);

	List<StudentResponse> importStudents(ImportStudentRequest request);

	StudentStatisticsResponse statistics();

}
