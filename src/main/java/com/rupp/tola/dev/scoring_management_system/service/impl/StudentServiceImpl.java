package com.rupp.tola.dev.scoring_management_system.service.impl;

import java.util.*;

import com.rupp.tola.dev.scoring_management_system.dto.request.ImportStudentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.StudentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.StudentResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Class;
import com.rupp.tola.dev.scoring_management_system.entity.Student;
import com.rupp.tola.dev.scoring_management_system.exception.ResourceNotFoundException;
import com.rupp.tola.dev.scoring_management_system.mapper.StudentsMapper;
import com.rupp.tola.dev.scoring_management_system.repository.ClassesRepository;
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
	private final ExcelService excelService;

	@Override
	public StudentResponse create(StudentRequest request) {
		Student student = studentsMapper.toEntity(request);
		student.setStudentCode(StudentCodeGenerateUtils.generator());
		student.setStatus(true);
		
		Class aClass = classesRepository.findById(request.getClassId())
				.orElseThrow(() -> new ResourceNotFoundException("Classes not found: " + request.getClassId()));
		student.setClazz(aClass);
		Student saved = studentsRepository.save(student);
		return studentsMapper.toResponse(saved);
	}

	@Override
	public StudentResponse getById(UUID uuid) {
		Student student = studentsRepository.findById(uuid)
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

		Page<Student> students = studentsRepository.findAll(pageable);
		log.info("Students found with all {}", students.getContent());

		return students.map(studentsMapper::toResponse);
	}

	@Override
	public Optional<StudentResponse> findByClazzId(UUID id) {
		return studentsRepository.findByClazzId(id)
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
		Student student = studentsRepository.findById(uuid)
				.orElseThrow(() -> new NoSuchElementException("Student not found with ID: " + uuid));

		student.setStudentCode(StudentCodeGenerateUtils.generator());
		student.setClazz(studentsMapper.map(studentRequest.getClassId()));
		student.setKhFirstName(studentRequest.getKhFirstName());
		student.setKhLastName(studentRequest.getKhLastName());
		student.setEnFirstName(studentRequest.getEnFirstName());
		student.setEnLastName(studentRequest.getEnLastName());
		student.setGender(studentRequest.getGender());
		student.setDateOfBirth(Util.convertToLocalDate(studentRequest.getDateOfBirth()));
		student.setEmail(studentRequest.getEmail());
		student.setPhoneNumber(studentRequest.getPhoneNumber());
		student.setAddress(studentRequest.getAddress());

		Student updated = studentsRepository.save(student);
		log.info("Student Update with ID: {}", uuid);
        return studentsMapper.toResponse(updated);
	}

	@Override
	public void delete(UUID uuid) {
		Student student = studentsRepository.findById(uuid)
			.orElseThrow(() -> new ResourceNotFoundException("Student not found with ID : " + uuid));
		log.info("Student delete with id {}", student.getId());
		studentsRepository.delete(student);
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

	@Override
	public List<StudentResponse> importStudent(ImportStudentRequest request) {
		if(request.getFile() != null && request.getFile().isEmpty()) {
			throw new ResourceNotFoundException("Student file not found");
		}
		
		List<Student> studentList = excelService.exportStudents(request.getFile());
		if (studentList.isEmpty()) {
			log.warn("No students found in the imported file");
			throw new RuntimeException("No students found in the imported file");
		}

		Class aClass;
		try {
			UUID classId = UUID.fromString(request.getClassId());
			aClass = classesRepository.findById(classId)
					.orElseThrow(() -> new ResourceNotFoundException("Classes not found: " + request.getClassId()));
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Invalid Class ID format: " + request.getClassId());
		}

		for (Student student : studentList) {
			student.setStudentCode(StudentCodeGenerateUtils.generator());
			student.setStatus(true);
			student.setClazz(aClass);
		}
		
		List<Student> savedStudents = studentsRepository.saveAll(studentList);
		log.info("Imported {} students successfully", savedStudents.size());


		//create response as list mapper later
		return studentList.stream()
				.map(studentsMapper::toResponse)
				.toList();
	}
}

