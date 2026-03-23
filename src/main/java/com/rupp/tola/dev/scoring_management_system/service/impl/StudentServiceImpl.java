package com.rupp.tola.dev.scoring_management_system.service.impl;

import java.util.*;

import com.rupp.tola.dev.scoring_management_system.dto.request.ImportStudentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.StudentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.StudentResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Class;
import com.rupp.tola.dev.scoring_management_system.entity.Student;
import com.rupp.tola.dev.scoring_management_system.exception.ResourceNotFoundException;
import com.rupp.tola.dev.scoring_management_system.mapper.StudentAddressMapper;
import com.rupp.tola.dev.scoring_management_system.mapper.StudentsMapper;
import com.rupp.tola.dev.scoring_management_system.repository.ClassRepository;
import com.rupp.tola.dev.scoring_management_system.service.ClassService;
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
	private final StudentsMapper studentsMapper;
	private final StudentAddressMapper studentAddressMapper;
	private final ExcelService excelService;

	@Override
	public StudentResponse create(StudentRequest request) {
		Student student = studentsMapper.toEntity(request);
		student.setStudentCode(StudentCodeGenerateUtils.generator());
		student.setStatus(true);
		
		Class clazz = classRepository.findById(request.getClassId())
				.orElseThrow(() -> new ResourceNotFoundException("Classes not found: " + request.getClassId()));

		student.setClazz(clazz);

		if (request.getAddress() != null) {
			student.getAddress().setStudent(student);
		}

		Student saved = studentRepository.save(student);
		return studentsMapper.toResponse(saved);
	}

	@Override
	public StudentResponse getById(UUID uuid) {
		Student student =  this.findByOrThrow(uuid);
		log.info("Student found with id {}", student.getId());
		return studentsMapper.toResponse(student);
	}

	@Override
	public Page<StudentResponse> getAll(Pageable pageable) {
		Page<Student> students = studentRepository.findAll(pageable);
		log.info("Students found with all {}", students.getContent());
		return students.map(studentsMapper::toResponse);
	}

	@Override
	public StudentResponse update(UUID uuid, StudentRequest request) {
		Student student = this.findByOrThrow(uuid);
		student.setStudentCode(StudentCodeGenerateUtils.generator());
		student.setClazz(classRepository.findById(request
				.getClassId()).orElseThrow(() -> new ResourceNotFoundException("Classes not found: " + request.getClassId())));
		student.setKhFirstName(request.getKhFirstName());
		student.setKhLastName(request.getKhLastName());
		student.setEnFirstName(request.getEnFirstName());
		student.setEnLastName(request.getEnLastName());
		student.setGender(request.getGender());
		student.setDateOfBirth(Util.convertToLocalDate(request.getDateOfBirth()));
		student.setEmail(request.getEmail());
		student.setPhoneNumber(request.getPhoneNumber());
		
		if (request.getAddress() != null) {
			if (student.getAddress() == null) {
				student.setAddress(studentAddressMapper.toEntity(request.getAddress()));
				student.getAddress().setStudent(student);
			} else {
				// Manual update of address fields as requested "menaul"
				student.getAddress().setHouseNumber(request.getAddress().getHouseNumber());
				student.getAddress().setStreet(request.getAddress().getStreet());
				student.getAddress().setSangkat(request.getAddress().getSangkat());
				student.getAddress().setKhan(request.getAddress().getKhan());
				student.getAddress().setProvince(request.getAddress().getProvince());
				student.getAddress().setCountry(request.getAddress().getCountry());
			}
		}

		Student updated = studentRepository.save(student);
		log.info("Student Update with ID: {}", uuid);
        return studentsMapper.toResponse(updated);
	}

	@Override
	public void delete(UUID uuid) {
		Student student = this.findByOrThrow(uuid);
		log.info("Student delete with id {}", student.getId());
		studentRepository.delete(student);
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
		}
		
		List<Student> savedStudents = studentRepository.saveAll(studentList);
		log.info("Imported {} students successfully", savedStudents.size());

		return studentList.stream()
				.map(studentsMapper::toResponse)
				.toList();
	}

	private Student findByOrThrow(UUID uuid) {
		return studentRepository
				.findById(uuid)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found with id : " + uuid));
	}


}

