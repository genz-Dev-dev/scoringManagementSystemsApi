package com.rupp.tola.dev.scoring_management_system.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.entity.Class;
import org.springframework.stereotype.Service;

import com.rupp.tola.dev.scoring_management_system.exception.ResourceNotFoundException;
import com.rupp.tola.dev.scoring_management_system.repository.ClassesRepository;
import com.rupp.tola.dev.scoring_management_system.service.ClassesService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassesServiceImpl implements ClassesService {
	private final ClassesRepository classesRepository;

	@Override
	public Class createClasses(Class aClass) {
		return classesRepository.save(aClass);
	}

	@Override
	public List<Class> getClasses(Boolean status) {
		return Optional.of(classesRepository.findBystatus(status)).filter(list -> !list.isEmpty())
				.orElseThrow(() -> new ResourceNotFoundException("No classes found" + status));
	}

	@Override
	public Class editclasses(UUID id, Class aClass) {
		Class class2 = getById(id);
		class2.setName(aClass.getName());
		return classesRepository.save(class2);
	}

	@Override
	public Class getById(UUID id) {
		String message = "class id is not have.!";
		return classesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(message + " " + id));
	}

}
