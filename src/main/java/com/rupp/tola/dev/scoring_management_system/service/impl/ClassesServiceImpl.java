package com.rupp.tola.dev.scoring_management_system.service.impl;

import org.springframework.stereotype.Service;

import com.rupp.tola.dev.scoring_management_system.entity.Classes;
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

}
