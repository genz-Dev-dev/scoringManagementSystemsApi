package com.kh.rupp_dev.boukryuniversity.service.impl;

import com.kh.rupp_dev.boukryuniversity.dto.request.ClassRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.ClassResponse;
import com.kh.rupp_dev.boukryuniversity.dto.response.DepartmentResponse;
import com.kh.rupp_dev.boukryuniversity.entity.Class;
import com.kh.rupp_dev.boukryuniversity.entity.Department;
import com.kh.rupp_dev.boukryuniversity.exception.DuplicateResourceException;
import com.kh.rupp_dev.boukryuniversity.exception.ResourceNotFoundException;
import com.kh.rupp_dev.boukryuniversity.mapper.ClassMapper;
import com.kh.rupp_dev.boukryuniversity.mapper.DepartmentMapper;
import com.kh.rupp_dev.boukryuniversity.repository.ClassRepository;
import com.kh.rupp_dev.boukryuniversity.repository.DepartmentRepository;
import com.kh.rupp_dev.boukryuniversity.service.ClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {
	private final ClassRepository classRepository;
	private final DepartmentRepository departmentRepository;
	private final ClassMapper classMapper;
	private final DepartmentMapper departmentMapper;

	@Override
	public ClassResponse create(ClassRequest request) {
		validateDuplicate(request);
		Class clazz = classMapper.toEntity(request);
		clazz.setDepartment(findDepartmentById(request.getDepartmentId()));

		Class saved = classRepository.save(clazz);
		log.info("Class created: {}", saved);
		return classMapper.toResponse(saved);
	}

	@Override
	public Page<ClassResponse> getAll(Pageable pageable) {
		Page<Class> classes = classRepository.findAll(pageable);
		return classes.map(classMapper::toResponse);
	}

	@Override
	@Transactional
	public ClassResponse update(UUID id, ClassRequest request) {
		Class clazz = findByOrThrow(id);
		validateDuplicate(id, request);
		classMapper.updateFromRequest(request, clazz);
		clazz.setDepartment(findDepartmentById(request.getDepartmentId()));

		Class saved = classRepository.save(clazz);
		log.info("Class with id {} updated successfully", saved.getId());
		return classMapper.toResponse(saved);
	}

	@Override
	public void delete(UUID id) {
		Class clazz = findByOrThrow(id);
		log.info("Delete class successfully with ID: " + id);
		classRepository.delete(clazz);
	}

	@Override
	public DepartmentResponse findByDepartmentId(UUID departmentId, UUID classId) {
		Class clazz = findByOrThrow(classId);
		Department department = classRepository.findByDepartment(clazz.getDepartment())
				.orElseThrow(() -> new ResourceNotFoundException("Department ot found with ID: " + clazz.getDepartment().getId()));
		return departmentMapper.toResponse(department);
	}

	@Override
	public ClassResponse getById(UUID id) {
		Class clazz = findByOrThrow(id);
		log.info("Class with id {} retrieved successfully", id);
		return classMapper.toResponse(clazz);
	}

	@Transactional(readOnly = true)
	protected Class findByOrThrow(UUID id) {
		return classRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + id));
	}

	private Department findDepartmentById(String departmentId) {
		UUID id;
		try {
			id = UUID.fromString(departmentId);
		} catch (IllegalArgumentException ex) {
			throw new IllegalArgumentException("Department id must be a valid UUID string");
		}

		return departmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + departmentId));
	}

	private void validateDuplicate(ClassRequest request) {
		if (classRepository.existsByNameAndAcademicYearAndGeneration(
				request.getName(), request.getAcademicYear(), request.getGeneration())) {
			throw new DuplicateResourceException("Class already exists");
		}
	}

	private void validateDuplicate(UUID id, ClassRequest request) {
		if (classRepository.existsByNameAndAcademicYearAndGenerationAndIdNot(
				request.getName(), request.getAcademicYear(), request.getGeneration(), id)) {
			throw new DuplicateResourceException("Class already exists");
		}
	}


}
