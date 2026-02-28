package com.rupp.tola.dev.scoring_management_system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rupp.tola.dev.scoring_management_system.dto.ClassDTO;
import com.rupp.tola.dev.scoring_management_system.entity.Classes;
import com.rupp.tola.dev.scoring_management_system.mapper.ClassesMapper;
import com.rupp.tola.dev.scoring_management_system.service.ClassesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/req/class")
public class ClassesController {

	private final ClassesService classesService;

//	@PostMapping
//	public ResponseEntity<?> createClass(@RequestBody ClassDTO classDTO) {
//		Classes classes2 = ClassesMapper.INSTANCE.toClass(classDTO);
//		classes2 = classesService.createClasses(classes2);
//		return ResponseEntity.ok(ClassesMapper.INSTANCE.toClass(classDTO));
//	}

	@PostMapping
	public ResponseEntity<ClassDTO> createClass(@RequestBody ClassDTO classDTO) {
		Classes classes = ClassesMapper.INSTANCE.toClass(classDTO);
		Classes saved = classesService.createClasses(classes);
		return ResponseEntity.ok(ClassesMapper.INSTANCE.toClassesDto(saved)); // ← return saved object
	}

}
