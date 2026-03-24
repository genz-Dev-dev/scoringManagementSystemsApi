package com.rupp.tola.dev.scoring_management_system.service.impl;

import java.util.*;

import com.rupp.tola.dev.scoring_management_system.dto.request.StudentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.StudentResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Classes;
import com.rupp.tola.dev.scoring_management_system.exception.ResourceNotFoundException;
import com.rupp.tola.dev.scoring_management_system.mapper.StudentsMapper;
import com.rupp.tola.dev.scoring_management_system.repository.ClassesRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rupp.tola.dev.scoring_management_system.entity.Students;
import com.rupp.tola.dev.scoring_management_system.repository.StudentsRepository;
import com.rupp.tola.dev.scoring_management_system.service.StudentService;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

	private final StudentsRepository studentsRepository;
	private final ClassesRepository classesRepository;
	private final StudentsMapper studentsMapper;

	@Override
	public StudentResponse create(StudentRequest request) {
		Students students = studentsMapper.toEntity(request);
		Classes classes = classesRepository.findById(request.getClassId())
				.orElseThrow(() -> new ResourceNotFoundException("Classes not found: " + request.getClassId()));
		students.setClasses(classes);
		Students saved = studentsRepository.save(students);
		return studentsMapper.toResponse(saved);
	}

	@Override
	public StudentResponse getById(UUID uuid) {
		Students students = studentsRepository.findById(uuid)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found with id : " + uuid));
		log.info("Student found with id {}", students.getId());
		return studentsMapper.toResponse(students);
	}

	@Override
	public Page<StudentResponse> getAll(Map<String, String> param) {
		int number = Integer.parseInt(param.getOrDefault("number", "0"));
		int size = Integer.parseInt(param.getOrDefault("size", "15"));
		String sort = param.getOrDefault("sort", "ASC");
		String property = param.getOrDefault("property", "id");

		Sort.Direction direction = sort.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(number, size, Sort.by(direction, property));

		Page<Students> students = studentsRepository.findAll(pageable);
		log.info("Students found with all {}", students.getContent());

		return students.map(studentsMapper::toResponse);
	}

	@Override
	public Optional<StudentResponse> findByClassesId(UUID id) {
		return studentsRepository.findByClassesId(id)
				.map(studentsMapper::toResponse);
	}

	@Override
	public Page<StudentResponse> findByStatus(boolean status, Pageable pageable) {
		log.info("findByStatus: {}, pageable: {}", status, pageable);
		return studentsRepository.findByStatus(status, pageable)
				.map(studentsMapper::toResponse);
	}

	@Override
	public StudentResponse update(UUID uuid, StudentRequest studentRequest) {
		Students students = studentsRepository.findById(uuid)
				.orElseThrow(() -> new NoSuchElementException("Student not found with ID: " + uuid));

		students.setStudentCode(studentRequest.getStudentCode());
		students.setClasses(studentsMapper.map(studentRequest.getClassId()));
		students.setKhFirstName(studentRequest.getKhFirstName());
		students.setKhLastName(studentRequest.getKhLastName());
		students.setEnFirstname(studentRequest.getEnFirstName());
		students.setEnlastName(studentRequest.getEnLastName());
		students.setGender(studentRequest.getGender());
		students.setDateOfBirth(studentRequest.getDateOfBirth());
		students.setEmail(studentRequest.getEmail());
		students.setPhoneNumber(studentRequest.getPhoneNumber());
		students.setAddress(studentRequest.getAddress());

		Students updated = studentsRepository.save(students);
		log.info("Student Update with ID: {}", uuid);
        return studentsMapper.toResponse(updated);
	}

	@Override
	public void delete(UUID uuid) {
		Students students = studentsRepository.findById(uuid)
			.orElseThrow(() -> new ResourceNotFoundException("Student not found with ID : " + uuid));
		log.info("Student delete with id {}", students.getId());
		studentsRepository.delete(students);
	}

	@Override
	public Page<StudentResponse> getByStatusPagination(Map<String, String> param, Boolean status) {
		int number = Integer.parseInt(param.getOrDefault("number", "0"));
		int size = Integer.parseInt(param.getOrDefault("size", "15"));
		String sort = param.getOrDefault("sort", "ASC");
		String property = param.getOrDefault("property", "id");
		Sort.Direction direction = sort.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(number, size, Sort.by(direction, property));
		return studentsRepository.findByStatus(status, pageable)
				.map(studentsMapper::toResponse);
	}

}

