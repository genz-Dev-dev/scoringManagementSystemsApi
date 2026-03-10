package com.rupp.tola.dev.scoring_management_system.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.rupp.tola.dev.scoring_management_system.entity.Classes;
import com.rupp.tola.dev.scoring_management_system.exception.ResourceNotFoundException;
import com.rupp.tola.dev.scoring_management_system.repository.ClassesRepository;
import com.rupp.tola.dev.scoring_management_system.service.ClassesService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassesServiceImpl implements ClassesService {
	private final ClassesRepository classesRepository;

	@Override
	public Classes createClasses(Classes classes) {
		return classesRepository.save(classes);
	}

	@Override
	public List<Classes> getClasses(Boolean status) {
		return Optional.of(classesRepository.findBystatus(status)).filter(list -> !list.isEmpty())
				.orElseThrow(() -> new ResourceNotFoundException("No classes found", status));
	}

	@Override
	public Classes editclasses(UUID id, Classes classes) {
		Classes classes2 = getById(id);
		classes2.setName(classes.getName());
		return classesRepository.save(classes2);
	}

	@Override
	public Classes getById(UUID id) {
		String message = "class id is not have.!";
		return classesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(message, id));
	}

}
