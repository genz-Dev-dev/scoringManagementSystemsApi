package com.rupp.tola.dev.scoring_management_system.service.impl;

import java.util.*;

import com.rupp.tola.dev.scoring_management_system.dto.request.ImportStudentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.StudentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.StudentResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Class;
import com.rupp.tola.dev.scoring_management_system.entity.Student;
import com.rupp.tola.dev.scoring_management_system.exception.ResourceNotFoundException;
import com.rupp.tola.dev.scoring_management_system.mapper.StudentsMapper;
import com.rupp.tola.dev.scoring_management_system.repository.ClassRepository;
import com.rupp.tola.dev.scoring_management_system.service.ExcelService;
import com.rupp.tola.dev.scoring_management_system.util.StudentCodeGenerateUtils;
import com.rupp.tola.dev.scoring_management_system.util.Util;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rupp.tola.dev.scoring_management_system.repository.StudentRepository;
import com.rupp.tola.dev.scoring_management_system.service.StudentService;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;
	private final ClassRepository classRepository;
	private final StudentsMapper studentsMapper;
	private final ExcelService excelService;

	@Override
	public StudentResponse create(StudentRequest request) {
		Student student = studentsMapper.toEntity(request);
		student.setStudentCode(StudentCodeGenerateUtils.generator());
		student.setStatus(true);
		
		Class aClass = classRepository.findById(request.getClassId())
				.orElseThrow(() -> new ResourceNotFoundException("Classes not found: " + request.getClassId()));
		student.setClazz(aClass);
		Student saved = studentRepository.save(student);
		return studentsMapper.toResponse(saved);
	}

	@Override
	public StudentResponse getById(UUID uuid) {
		Student student = studentRepository.findById(uuid)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found with id : " + uuid));
		log.info("Student found with id {}", student.getId());
		return studentsMapper.toResponse(student);
	}

	@Override
	public Page<StudentResponse> getAll(Map<String, String> param) {
		int number = Integer.parseInt(param.getOrDefault("number", "0"));
		int size = Integer.parseInt(param.getOrDefault("size", "15"));
		String sort = param.getOrDefault("sort", "ASC");
		String property = param.getOrDefault("property", "id");

		Sort.Direction direction = sort.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(number, size, Sort.by(direction, property));

		Page<Student> students = studentRepository.findAll(pageable);
		log.info("Students found with all {}", students.getContent());

		return students.map(studentsMapper::toResponse);
	}

	@Override
	public Optional<StudentResponse> findByClazzId(UUID id) {
		return studentRepository.findByClazzId(id)
				.map(studentsMapper::toResponse);
	}

	@Override
	public Page<StudentResponse> findByStatus(boolean status, Pageable pageable) {
		log.info("findByStatus: {}, pageable: {}", status, pageable);
		return studentRepository.findByStatus(status, pageable)
				.map(studentsMapper::toResponse);
	}

	@Override
	public StudentResponse update(UUID uuid, StudentRequest request) {
		Student student = studentRepository.findById(uuid)
				.orElseThrow(() -> new NoSuchElementException("Student not found with ID: " + uuid));

		student.setStudentCode(StudentCodeGenerateUtils.generator());
		student.setClazz(studentsMapper.map(request.getClassId()));
		student.setKhFirstName(request.getKhFirstName());
		student.setKhLastName(request.getKhLastName());
		student.setEnFirstName(request.getEnFirstName());
		student.setEnLastName(request.getEnLastName());
		student.setGender(request.getGender());
		student.setDateOfBirth(Util.convertToLocalDate(request.getDateOfBirth()));
		student.setEmail(request.getEmail());
		student.setPhoneNumber(request.getPhoneNumber());
		student.setAddress(request.getAddress());

		Student updated = studentRepository.save(student);
		log.info("Student Update with ID: {}", uuid);
        return studentsMapper.toResponse(updated);
	}

	@Override
	public void delete(UUID uuid) {
		Student student = studentRepository.findById(uuid)
			.orElseThrow(() -> new ResourceNotFoundException("Student not found with ID : " + uuid));
		log.info("Student delete with id {}", student.getId());
		studentRepository.delete(student);
	}

	@Override
	public Page<StudentResponse> getByStatusPagination(Map<String, String> param, Boolean status) {
		int number = Integer.parseInt(param.getOrDefault("number", "0"));
		int size = Integer.parseInt(param.getOrDefault("size", "15"));
		String sort = param.getOrDefault("sort", "ASC");
		String property = param.getOrDefault("property", "id");
		Sort.Direction direction = sort.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(number, size, Sort.by(direction, property));
		return studentRepository.findByStatus(status, pageable)
				.map(studentsMapper::toResponse);
	}

	@Override
	public List<StudentResponse> importStudents(ImportStudentRequest request) {
		if(request.getFile() != null && request.getFile().isEmpty()) {
			throw new ResourceNotFoundException("Student file not found");
		}
		
		List<Student> studentList = excelService.importStudents(request.getFile());
		if (studentList.isEmpty()) {
			log.warn("No students found in the imported file");
			throw new RuntimeException("No students found in the imported file");
		}

		Class aClass;
		try {
			UUID classId = UUID.fromString(request.getClassId());
			aClass = classRepository.findById(classId)
					.orElseThrow(() -> new ResourceNotFoundException("Classes not found: " + request.getClassId()));
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Invalid Class ID format: " + request.getClassId());
		}

		for (Student student : studentList) {
			student.setStudentCode(StudentCodeGenerateUtils.generator());
			student.setStatus(true);
			student.setClazz(aClass);
		}
		
		List<Student> savedStudents = studentRepository.saveAll(studentList);
		log.info("Imported {} students successfully", savedStudents.size());

		return studentList.stream()
				.map(studentsMapper::toResponse)
				.toList();
	}


}

