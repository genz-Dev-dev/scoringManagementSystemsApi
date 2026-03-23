package com.rupp.tola.dev.scoring_management_system.service;

import java.util.List;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.dto.request.ClassRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ClassResponse;

public interface ClassesService {
	ClassResponse create(ClassRequest request);

	List<ClassResponse> getAllByStatus(Boolean status);

	ClassResponse getById(UUID id);

	ClassResponse update(UUID id, ClassRequest request);
}
