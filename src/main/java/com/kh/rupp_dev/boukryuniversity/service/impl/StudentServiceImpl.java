package com.kh.rupp_dev.boukryuniversity.service.impl;

import java.time.LocalDate;
import java.util.*;

import com.kh.rupp_dev.boukryuniversity.constant.CodePrefix;
import com.kh.rupp_dev.boukryuniversity.dto.response.ClassResponse;
import com.kh.rupp_dev.boukryuniversity.dto.request.ImportStudentRequest;
import com.kh.rupp_dev.boukryuniversity.dto.request.StudentRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.StudentResponse;
import com.kh.rupp_dev.boukryuniversity.dto.response.StudentStatisticsResponse;
import com.kh.rupp_dev.boukryuniversity.dto.response.UploadBatchesResponse;
import com.kh.rupp_dev.boukryuniversity.entity.Class;
import com.kh.rupp_dev.boukryuniversity.entity.Student;
import com.kh.rupp_dev.boukryuniversity.exception.ResourceNotFoundException;
import com.kh.rupp_dev.boukryuniversity.mapper.ClassMapper;
import com.kh.rupp_dev.boukryuniversity.mapper.StudentAddressMapper;
import com.kh.rupp_dev.boukryuniversity.mapper.StudentMapper;
import com.kh.rupp_dev.boukryuniversity.repository.ClassRepository;
import com.kh.rupp_dev.boukryuniversity.service.ExcelService;
import com.kh.rupp_dev.boukryuniversity.utils.Util;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kh.rupp_dev.boukryuniversity.repository.StudentRepository;
import com.kh.rupp_dev.boukryuniversity.service.StudentService;

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
		student.setEnrollmentDate(LocalDate.now());
		student.getAddress().setStudent(student);
		student.setStudentCode(getStudentCode());
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
	public UploadBatchesResponse importStudents(ImportStudentRequest request) {
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
			student.setStudentCode(getStudentCode());
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
			student.setStudentCode(getStudentCode());
		}

		if (student.getStatus() == null) {
			student.setStatus(true);
		}

		student.setGender(normalizeGender(request.getGender()));
		student.setDateOfBirth(Util.convertToLocalDate(request.getDateOfBirth()));
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

	private String getStudentCode() {
		Long codeNumber = studentRepository.getNextSequence();
		return CodePrefix.STUDENT_CODE_PREFIX + String.format("%04d", codeNumber);
	}


}
