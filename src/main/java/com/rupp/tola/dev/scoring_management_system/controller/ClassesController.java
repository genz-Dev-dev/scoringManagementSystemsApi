package com.rupp.tola.dev.scoring_management_system.controller;

import java.util.List;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.data.SingleResponse;
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
public class ClassesController {

	private final ClassService classService;

	@PostMapping
	public ResponseEntity<SingleResponse<ClassResponse>> create(@Valid @RequestBody ClassRequest classRequest) {
		ClassResponse response = classService.create(classRequest);
		return ResponseEntity.ok(SingleResponse.success("Successfully created class.", response));
	}

	@GetMapping
	public ResponseEntity<SingleResponse<List<ClassResponse>>> getAll(@RequestParam(defaultValue = "false") Boolean status) {
		List<ClassResponse> responses = classService.getAllByStatus(status);
		return ResponseEntity.ok(SingleResponse.success("Successfully retrieved classes.", responses));
	}

	@PutMapping("/{id}")
	public ResponseEntity<SingleResponse<ClassResponse>> getById(@PathVariable UUID id,
			@RequestBody @Valid ClassRequest classRequest) {
		ClassResponse response = classService.update(id, classRequest);
		return ResponseEntity.ok(SingleResponse.success("Successfully updated class.", response));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<SingleResponse<ClassResponse>> deleteById(@PathVariable UUID id) {
		classService.delete(id);
		return ResponseEntity.ok(SingleResponse.success("Successfully to delete class." , null));
	}
}
