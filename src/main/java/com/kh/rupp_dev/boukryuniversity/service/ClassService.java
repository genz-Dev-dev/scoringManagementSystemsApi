package com.kh.rupp_dev.boukryuniversity.service;

import java.util.UUID;

import com.kh.rupp_dev.boukryuniversity.dto.request.ClassRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.ClassResponse;
import com.kh.rupp_dev.boukryuniversity.dto.response.DepartmentResponse;
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
