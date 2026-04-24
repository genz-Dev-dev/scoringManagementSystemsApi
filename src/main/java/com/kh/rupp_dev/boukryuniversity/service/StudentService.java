package com.kh.rupp_dev.boukryuniversity.service;

import java.util.List;
import java.util.UUID;

import com.kh.rupp_dev.boukryuniversity.dto.response.ClassResponse;
import com.kh.rupp_dev.boukryuniversity.dto.request.ImportStudentRequest;
import com.kh.rupp_dev.boukryuniversity.dto.request.StudentRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.StudentResponse;
import com.kh.rupp_dev.boukryuniversity.dto.response.StudentStatisticsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface StudentService {

	StudentResponse create(StudentRequest request);

	StudentResponse getById(UUID uuid);

	Page<StudentResponse> getAll(Pageable pageable);

	StudentResponse update(UUID uuid, StudentRequest request);

	void delete(UUID uuid);

	ClassResponse getClassByStudentId(UUID uuid);

	List<StudentResponse> importStudents(ImportStudentRequest request);

	StudentStatisticsResponse statistics();

}
