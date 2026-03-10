package com.rupp.tola.dev.scoring_management_system.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.rupp.tola.dev.scoring_management_system.entity.Students;
import com.rupp.tola.dev.scoring_management_system.exception.ResourceNotFoundException;
import com.rupp.tola.dev.scoring_management_system.repository.StudentsRepository;
import com.rupp.tola.dev.scoring_management_system.service.StudentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
	private final StudentsRepository studentsRepository;

	@Override
	public Students createStudents(Students students) {
		return studentsRepository.save(students);
	}

	@Override
	public Students getById(UUID id) {

		return studentsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("student", id));
	}

	@Override
	public Optional<Students> findByClassesId(UUID id) {
		return studentsRepository.findByClassesId(id);
	}

	@Override
	public List<Students> getStudents() {
		return studentsRepository.findAll();
	}

	@Override
	public Page<Students> getByStatusPagination(Map<String, String> param, Boolean status) {
		
		return null;
	}

//	@Override
//	public List<Students> getByStatus(Boolean status) {
//		String message = "No Students Found with status";
//		return studentsRepository.findByStatus(status)
//				.filter(List -> !List.isEmpty())
//				.orElseThrow(() -> new ResourceNotFoundException(message, status));
//	}

}
