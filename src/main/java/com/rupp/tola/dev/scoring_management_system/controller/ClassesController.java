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

import com.rupp.tola.dev.scoring_management_system.dto.ClassDTO;
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
	public ResponseEntity<ClassDTO> createClass(@RequestBody ClassDTO classDTO) {
		Class aClass = ClassesMapper.INSTANCE.toClass(classDTO);
		Class saved = classesService.createClasses(aClass);
		return ResponseEntity.ok(ClassesMapper.INSTANCE.toClassesDto(saved)); // ← return saved object
	}

	@GetMapping
	public ResponseEntity<List<Class>> getClasses(@RequestParam(defaultValue = "false") Boolean status) {
		return ResponseEntity.ok(classesService.getClasses(status));
	}

	@PutMapping("{classId}")
	public ResponseEntity<ClassDTO> updateClasses(@PathVariable("classId") UUID id,
			@RequestBody @Valid ClassDTO classDTO) {
		Class aClass = ClassesMapper.INSTANCE.toClass(classDTO);
		aClass = classesService.editclass(id, aClass);
		return ResponseEntity.ok(ClassesMapper.INSTANCE.toClassesDto(aClass));
	}
}
