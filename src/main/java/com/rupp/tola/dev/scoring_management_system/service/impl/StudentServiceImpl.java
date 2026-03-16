package com.rupp.tola.dev.scoring_management_system.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.rupp.tola.dev.scoring_management_system.dto.request.StudentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.StudentResponse;
import com.rupp.tola.dev.scoring_management_system.exception.ResourceNotFoundException;
import com.rupp.tola.dev.scoring_management_system.mapper.StudentsMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.rupp.tola.dev.scoring_management_system.entity.Students;
import com.rupp.tola.dev.scoring_management_system.repository.StudentsRepository;
import com.rupp.tola.dev.scoring_management_system.service.StudentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

	private final StudentsRepository studentsRepository;
	private final StudentsMapper studentsMapper;

	@Override
	public StudentResponse createStudents(StudentRequest request) {
		Students students = studentsMapper.toEntity(request);
		Students saved = studentsRepository.save(students);
		return studentsMapper.toResponse(saved);
	}

	@Override
	public StudentResponse getById(UUID id) {
		Students students = studentsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found with id : " + id));
		return studentsMapper.toResponse(students);
	}

	@Override
	public Optional<StudentResponse> findByClassesId(UUID id) {
		return studentsRepository.findByClassesId(id)
				.map(studentsMapper::toResponse);
	}

	@Override
	public List<StudentResponse> getStudents() {
		return studentsRepository.findAll().stream()
				.map(studentsMapper::toResponse)
				.toList();
	}

	@Override
	public Page<StudentResponse> getByStatusPagination(Map<String, String> param, Boolean status) {
		
		return null;
	}

}