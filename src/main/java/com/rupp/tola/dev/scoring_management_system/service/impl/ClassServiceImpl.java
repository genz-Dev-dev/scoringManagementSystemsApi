package com.rupp.tola.dev.scoring_management_system.service.impl;

import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.dto.request.ClassRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ClassResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Class;
import com.rupp.tola.dev.scoring_management_system.entity.StudentAddress;
import com.rupp.tola.dev.scoring_management_system.exception.DuplicateResourceException;
import com.rupp.tola.dev.scoring_management_system.mapper.ClassMapper;
import com.rupp.tola.dev.scoring_management_system.mapper.StudentAddressMapper;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rupp.tola.dev.scoring_management_system.exception.ResourceNotFoundException;
import com.rupp.tola.dev.scoring_management_system.repository.ClassRepository;
import com.rupp.tola.dev.scoring_management_system.service.ClassService;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {
	private final ClassRepository classRepository;
	private final ClassMapper classMapper;
	private final StudentAddressMapper studentAddressMapper;
	private final EntityManager entityManager;

	@Override
	public ClassResponse create(ClassRequest request) {
		if (classRepository.existsByName(request.getName())) {
			throw new DuplicateResourceException("Class with name " + request.getName() + " already exists");
		}
		Class clazz = classMapper.toEntity(request);
		clazz.setStatus(true);

		if (clazz.getStudents() != null) {
			clazz.getStudents().forEach(student -> {
				student.setClazz(clazz);
				if (student.getAddress() != null) {
					student.getAddress().setStudent(student);
				}
			});
		}

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
	public ClassResponse update(UUID id, ClassRequest request) {
		Class clazz = findByOrThrow(id);
		classMapper.updateFromRequest(request, clazz);

		if (clazz.getStudents() != null) {
			clazz.getStudents().forEach(student -> {
				student.setClazz(clazz);
				if (student.getAddress() != null) {
					StudentAddress address = student.getAddress();
					address.setStudent(student);

					if (address.getId() != null) {
						StudentAddress managedAddress = entityManager.merge(address);
						student.setAddress(managedAddress);
					}
				}
			});
		}

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

}
