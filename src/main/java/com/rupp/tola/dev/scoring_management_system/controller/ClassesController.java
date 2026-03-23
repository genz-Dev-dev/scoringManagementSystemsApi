package com.rupp.tola.dev.scoring_management_system.controller;

import java.util.List;
import java.util.UUID;

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
import com.rupp.tola.dev.scoring_management_system.entity.Class;
import com.rupp.tola.dev.scoring_management_system.mapper.ClassesMapper;
import com.rupp.tola.dev.scoring_management_system.service.ClassesService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/classes")
public class ClassesController {

	private final ClassesService classesService;

	@PostMapping
	public ResponseEntity<ClassResponse> createClass(@RequestBody ClassRequest classRequest) {
		Class aClass = ClassesMapper.INSTANCE.toClass(classRequest);
		Class saved = classesService.createClasses(aClass);
		return ResponseEntity.ok(ClassesMapper.INSTANCE.toClassesResponse(saved)); // ← return saved object
	}

	@GetMapping
	public ResponseEntity<List<Class>> getClasses(@RequestParam(defaultValue = "false") Boolean status) {
		return ResponseEntity.ok(classesService.getClasses(status));
	}

	@PutMapping("{classId}")
	public ResponseEntity<ClassResponse> updateClasses(@PathVariable("classId") UUID id,
			@RequestBody @Valid ClassRequest classRequest) {
		Class aClass = ClassesMapper.INSTANCE.toClass(classRequest);
		aClass = classesService.editclass(id, aClass);
		return ResponseEntity.ok(ClassesMapper.INSTANCE.toClassesResponse(aClass));
	}
}
