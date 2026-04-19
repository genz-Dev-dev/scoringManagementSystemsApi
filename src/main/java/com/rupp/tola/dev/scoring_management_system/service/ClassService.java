package com.rupp.tola.dev.scoring_management_system.service;

import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.dto.request.ClassRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ClassResponse;
import com.rupp.tola.dev.scoring_management_system.dto.response.DepartmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassService {

	ClassResponse create(ClassRequest request);

	Page<ClassResponse> getAll(Pageable pageable);

	ClassResponse getById(UUID id);

	ClassResponse update(UUID id, ClassRequest request);

    void delete(UUID id);

	@Deprecated
	DepartmentResponse findByDepartmentId(UUID departmentId , UUID classId);

}
