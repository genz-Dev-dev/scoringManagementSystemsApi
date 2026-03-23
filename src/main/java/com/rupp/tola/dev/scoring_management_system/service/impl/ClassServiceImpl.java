package com.rupp.tola.dev.scoring_management_system.service.impl;

import java.util.List;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.dto.request.ClassRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ClassResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Class;
import com.rupp.tola.dev.scoring_management_system.mapper.ClassesMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.rupp.tola.dev.scoring_management_system.exception.ResourceNotFoundException;
import com.rupp.tola.dev.scoring_management_system.repository.ClassRepository;
import com.rupp.tola.dev.scoring_management_system.service.ClassService;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {
	private final ClassRepository classRepository;
	private final ClassesMapper classesMapper;

	@Override
	public ClassResponse create(ClassRequest request) {
		Class clazz = classesMapper.toEntity(request);
		clazz.setStatus(true);
		Class saved = classRepository.save(clazz);
		return classesMapper.toResponse(saved);
	}

	@Override
	public List<ClassResponse> getAllByStatus(Boolean status) {
		List<Class> classes = classRepository.findByStatus(status);
		if (classes.isEmpty()) {
			throw new ResourceNotFoundException("No classes found with status: " + status);
		}
		return classes.stream()
				.map(classesMapper::toResponse)
				.toList();
	}

	@Override
	public ClassResponse update(UUID id, ClassRequest request) {
		Class classEntity = findByOrThrow(id);
		classesMapper.updateFromRequest(request, classEntity);
		Class saved = classRepository.save(classEntity);
		return classesMapper.toResponse(saved);
	}

	@Override
	public void delete(UUID id) {
		Class clazz = findByOrThrow(id);
		log.info("Delete class successfully with ID: " + id);
		classRepository.delete(clazz);
	}

	@Override
	public ClassResponse getById(UUID id) {
		Class classEntity = findByOrThrow(id);
		return classesMapper.toResponse(classEntity);
	}

	private Class findByOrThrow(UUID id) {
		return classRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + id));
	}

}
