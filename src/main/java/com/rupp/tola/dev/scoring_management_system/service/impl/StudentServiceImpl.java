package com.rupp.tola.dev.scoring_management_system.service.impl;

import java.util.*;

import com.rupp.tola.dev.scoring_management_system.dto.response.ClassResponse;
import com.rupp.tola.dev.scoring_management_system.dto.request.ImportStudentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.StudentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.StudentResponse;
import com.rupp.tola.dev.scoring_management_system.dto.response.StudentStatisticsResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Class;
import com.rupp.tola.dev.scoring_management_system.entity.Student;
import com.rupp.tola.dev.scoring_management_system.exception.ResourceNotFoundException;
import com.rupp.tola.dev.scoring_management_system.mapper.ClassMapper;
import com.rupp.tola.dev.scoring_management_system.mapper.StudentAddressMapper;
import com.rupp.tola.dev.scoring_management_system.mapper.StudentMapper;
import com.rupp.tola.dev.scoring_management_system.repository.ClassRepository;
import com.rupp.tola.dev.scoring_management_system.service.ExcelService;
import com.rupp.tola.dev.scoring_management_system.utils.StudentCodeGenerateUtils;
import com.rupp.tola.dev.scoring_management_system.utils.Util;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	private final ClassMapper classMapper;
	private final StudentMapper studentMapper;
	private final StudentAddressMapper studentAddressMapper;
	private final ExcelService excelService;

	@Override
	public StudentResponse create(StudentRequest request) {
		Student student = studentMapper.toEntity(request);
		Class clazz = classRepository.findById(request.getClassId())
						.orElseThrow(() -> new ResourceNotFoundException("Class not found"));
		student.setClazz(clazz);
		student.setDateOfBirth(Util.convertToLocalDate(request.getDateOfBirth()));
		student.setEnrollmentDate(Util.convertToLocalDate(request.getEnrollmentDate()));
		student.getAddress().setStudent(student);
		Student saved = studentRepository.save(student);
		return studentMapper.toResponse(saved);
	}

	@Override
	public StudentResponse getById(UUID uuid) {
		Student student =  this.findByOrThrow(uuid);
		log.info("Student found with id {}", student.getId());
		return studentMapper.toResponse(student);
	}

	@Override
	public Page<StudentResponse> getAll(Pageable pageable) {
		Page<Student> students = studentRepository.findAll(pageable);
		log.info("Students found with all {}", students.getContent());
		return students.map(studentMapper::toResponse);
	}

	@Override
	public StudentResponse update(UUID uuid, StudentRequest request) {
		Student student = this.findByOrThrow(uuid);
		studentMapper.updateFromRequest(request, student);
		applyRequest(student, request);

		Student updated = studentRepository.save(student);
		log.info("Student Update with ID: {}", uuid);
        return studentMapper.toResponse(updated);
	}

	@Override
	public void delete(UUID uuid) {
		Student student = this.findByOrThrow(uuid);
		log.info("Student delete with id {}", student.getId());
		studentRepository.delete(student);
	}

	@Override
	public ClassResponse getClassByStudentId(UUID uuid) {
		Student student = this.findByOrThrow(uuid);
		if (student.getClazz() == null) {
			throw new ResourceNotFoundException("Class not found for student with ID: " + uuid);
		}
		return classMapper.toResponse(student.getClazz());
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

		Class clazz;
		try {
			UUID classId = UUID.fromString(request.getClassId());
			clazz = classRepository.findById(classId)
					.orElseThrow(() -> new ResourceNotFoundException("Classes not found: " + request.getClassId()));
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Invalid Class ID format: " + request.getClassId());
		}

		for (Student student : studentList) {
			student.setStudentCode(StudentCodeGenerateUtils.generator());
			student.setStatus(true);
			student.setClazz(clazz);
			student.getAddress().setStudent(student);
		}
		
		List<Student> savedStudents = studentRepository.saveAll(studentList);
		log.info("Imported {} students successfully", savedStudents.size());

		return studentList.stream()
				.map(studentMapper::toResponse)
				.toList();
	}

	@Override
	public StudentStatisticsResponse statistics() {
		int female = studentRepository.countByGender("F");
		int male = studentRepository.countByGender("M");
		int total = male + female;
		return toStatistics(total, male, female);
	}

	private Student findByOrThrow(UUID uuid) {
		return studentRepository
				.findById(uuid)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found with id : " + uuid));
	}

	private void applyRequest(Student student, StudentRequest request) {
		if (student.getStudentCode() == null || student.getStudentCode().isBlank()) {
			student.setStudentCode(StudentCodeGenerateUtils.generator());
		}

		if (student.getStatus() == null) {
			student.setStatus(true);
		}

		student.setGender(normalizeGender(request.getGender()));
		student.setDateOfBirth(Util.convertToLocalDate(request.getDateOfBirth()));
		student.setEnrollmentDate(Util.convertToLocalDate(request.getEnrollmentDate()));
		student.setEmail(request.getEmail());
		student.setPhoneNumber(request.getPhoneNumber());

		if (request.getClassId() != null) {
			Class clazz = classRepository.findById(request.getClassId())
					.orElseThrow(() -> new ResourceNotFoundException("Classes not found: " + request.getClassId()));
			student.setClazz(clazz);
		}

		if (request.getAddress() != null) {
			if (student.getAddress() == null) {
				student.setAddress(studentAddressMapper.toEntity(request.getAddress()));
			} else {
				student.getAddress().setHouseNumber(request.getAddress().getHouseNumber());
				student.getAddress().setStreet(request.getAddress().getStreet());
				student.getAddress().setSangkat(request.getAddress().getSangkat());
				student.getAddress().setKhan(request.getAddress().getKhan());
				student.getAddress().setProvince(request.getAddress().getProvince());
				student.getAddress().setCountry(request.getAddress().getCountry());
			}
			student.getAddress().setStudent(student);
		}
	}

	private String normalizeGender(String gender) {
		if (gender == null) {
			return null;
		}

		String value = gender.trim().toUpperCase(Locale.ROOT);
		return switch (value) {
			case "M", "MALE" -> "M";
			case "F", "FEMALE" -> "F";
			default -> throw new IllegalArgumentException("Gender must be one of: male, female, M, F");
		};
	}

	private StudentStatisticsResponse toStatistics(int student ,int male ,int female) {
		return StudentStatisticsResponse
				.builder()
				.totalStudent(student)
				.totalFemale(female)
				.totalMale(male)
				.build();
	}


}
