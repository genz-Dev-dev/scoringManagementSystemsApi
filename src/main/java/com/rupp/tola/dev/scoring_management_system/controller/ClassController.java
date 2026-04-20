package com.rupp.tola.dev.scoring_management_system.controller;

import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.payload.MultipleResponse;
import com.rupp.tola.dev.scoring_management_system.payload.SingleResponse;
import com.rupp.tola.dev.scoring_management_system.dto.request.PaginationRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rupp.tola.dev.scoring_management_system.dto.request.ClassRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ClassResponse;
import com.rupp.tola.dev.scoring_management_system.service.ClassService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/classes")
public class ClassController {

	private final ClassService classService;

	@PostMapping
	public ResponseEntity<SingleResponse<ClassResponse>> create(@Valid @RequestBody ClassRequest classRequest) {
		ClassResponse response = classService.create(classRequest);
		return ResponseEntity.ok(SingleResponse.success("Successfully created class.", response));
	}

	@GetMapping
	public ResponseEntity<MultipleResponse<ClassResponse>> getAll(PaginationRequest request) {
		Page<ClassResponse> responses = classService.getAll(request.toPageable());
		return ResponseEntity
				.ok(MultipleResponse.success("Successfully retrieved all classes with pagination.", responses));
	}

	@GetMapping("/{id}")
	public ResponseEntity<SingleResponse<ClassResponse>> getById(@PathVariable UUID id) {
		ClassResponse response = classService.getById(id);
		return ResponseEntity.ok(SingleResponse.success("Successfully retrieved class.", response));
	}

	@PutMapping("/{id}")
	public ResponseEntity<SingleResponse<ClassResponse>> update(@PathVariable UUID id,
			@RequestBody @Valid ClassRequest classRequest) {
		ClassResponse response = classService.update(id, classRequest);
		return ResponseEntity.ok(SingleResponse.success("Successfully updated class.", response));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<SingleResponse<ClassResponse>> deleteById(@PathVariable UUID id) {
		classService.delete(id);
		return ResponseEntity.ok(SingleResponse.success("Successfully deleted class.", null));
	}
}
