package com.rupp.tola.dev.scoring_management_system.controller;

import java.util.List;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.data.SingleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rupp.tola.dev.scoring_management_system.dto.request.ClassRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ClassResponse;
import com.rupp.tola.dev.scoring_management_system.service.ClassesService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/classes")
public class ClassesController {

	private final ClassesService classesService;

	@PostMapping
	public ResponseEntity<SingleResponse<ClassResponse>> createClass(@Valid @RequestBody ClassRequest classRequest) {
		ClassResponse response = classesService.create(classRequest);
		return ResponseEntity.ok(SingleResponse.success("Successfully created class.", response));
	}

	@GetMapping
	public ResponseEntity<SingleResponse<List<ClassResponse>>> getClasses(@RequestParam(defaultValue = "false") Boolean status) {
		List<ClassResponse> responses = classesService.getAllByStatus(status);
		return ResponseEntity.ok(SingleResponse.success("Successfully retrieved classes.", responses));
	}

	@PutMapping("{classId}")
	public ResponseEntity<SingleResponse<ClassResponse>> updateClasses(@PathVariable("classId") UUID id,
			@RequestBody @Valid ClassRequest classRequest) {
		ClassResponse response = classesService.update(id, classRequest);
		return ResponseEntity.ok(SingleResponse.success("Successfully updated class.", response));
	}
}
