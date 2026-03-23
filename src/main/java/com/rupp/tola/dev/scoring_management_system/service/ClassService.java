package com.rupp.tola.dev.scoring_management_system.service;

import java.util.List;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.dto.request.ClassRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ClassResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassService {

	ClassResponse create(ClassRequest request);

	Page<ClassResponse> getAll(Pageable pageable);

	ClassResponse getById(UUID id);

	ClassResponse update(UUID id, ClassRequest request);

    void delete(UUID id);

}
