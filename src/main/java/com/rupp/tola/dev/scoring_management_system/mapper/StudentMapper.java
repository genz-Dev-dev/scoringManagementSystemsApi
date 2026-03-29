package com.rupp.tola.dev.scoring_management_system.mapper;

import java.util.List;

import com.rupp.tola.dev.scoring_management_system.dto.request.StudentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.StudentResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { StudentAddressMapper.class })
public interface StudentMapper {

	@Mapping(target = "id" , ignore = true)
	@Mapping(target = "clazz", ignore = true)
	@Mapping(target = "studentCode", ignore = true)
	@Mapping(target = "dateOfBirth", ignore = true)
	@Mapping(target = "enrollmentDate", ignore = true)
	Student toEntity(StudentRequest request);

	@Mapping(target = "address", source = "address")
	StudentResponse toResponse(Student student);

	List<StudentResponse> toList(List<Student> students);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "clazz", ignore = true)
	@Mapping(target = "scores", ignore = true)
	@Mapping(target = "studentCode", ignore = true)
	@Mapping(target = "dateOfBirth", ignore = true)
	@Mapping(target = "enrollmentDate", ignore = true)
	@Mapping(target = "address", source = "address")
	void updateFromRequest(StudentRequest request, @MappingTarget Student student);
	
}
