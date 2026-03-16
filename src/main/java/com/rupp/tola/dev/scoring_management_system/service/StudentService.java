package com.rupp.tola.dev.scoring_management_system.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.rupp.tola.dev.scoring_management_system.entity.Students;

import jakarta.servlet.http.HttpServletResponse;


public interface StudentService {

	Students createStudents(Students students);
	Students getById(UUID id);
	Optional<Students> findByClassesId(UUID id);
	List<Students> getStudents();
	Page<Students> getByStatusPagination(Map<String, String> param, Boolean status);
	Map<Integer, String> uploadStudents(MultipartFile file);
	void exportStudentsToExcelFile(HttpServletResponse response, List<Students> studentsList)throws IOException;

}
