package com.rupp.tola.dev.scoring_management_system.controller;

import java.util.List;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.data.MultipleResponse;
import com.rupp.tola.dev.scoring_management_system.data.SingleResponse;
import com.rupp.tola.dev.scoring_management_system.dto.request.PaginationRequest;
import com.rupp.tola.dev.scoring_management_system.entity.Class;
import com.rupp.tola.dev.scoring_management_system.mapper.ClassMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rupp.tola.dev.scoring_management_system.dto.request.ClassRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ClassResponse;
import com.rupp.tola.dev.scoring_management_system.service.ClassService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/classes")
public class ClassController {

	private final ClassService classService;
	private final ClassMapper classMapper;

	@PostMapping
	public ResponseEntity<SingleResponse<ClassResponse>> create(@Valid @RequestBody ClassRequest classRequest) {
		ClassResponse response = classService.create(classRequest);
		return ok(SingleResponse
				.success("Successfully created class.", response));
	}

	@GetMapping
	public ResponseEntity<MultipleResponse<ClassResponse>> getAll(PaginationRequest request) {
		Page<ClassResponse> responses = classService.getAll(request.toPageable());
		return ok(MultipleResponse
				.success("Successfully retrieved all classes with pagination.", responses));
	}

	@GetMapping("/allClass")
	public ResponseEntity<SingleResponse<List<ClassResponse>>> getAllClass() {

		List<ClassResponse> response = classService.findAll();

		return ResponseEntity.ok(
				SingleResponse.success("Successfully retrieved class.", response)
		);
	}
	@GetMapping("/{id}")
	public ResponseEntity<SingleResponse<ClassResponse>> getById(@PathVariable UUID id) {
		ClassResponse response = classService.getById(id);
		return ok(SingleResponse
				.success("Successfully retrieved class.", response));
	}

	@PutMapping("/{id}")
	public ResponseEntity<SingleResponse<ClassResponse>> update(@PathVariable UUID id,
			@RequestBody @Valid ClassRequest classRequest) {
		ClassResponse response = classService.update(id, classRequest);
		return ok(SingleResponse
				.success("Successfully updated class.", response));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<SingleResponse<ClassResponse>> deleteById(@PathVariable UUID id) {
		classService.delete(id);
		return ok(SingleResponse.success("Successfully deleted class.", null));
	}
}
