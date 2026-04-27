package com.kh.rupp_dev.boukryuniversity.mapper;

import java.util.List;

import com.kh.rupp_dev.boukryuniversity.dto.request.StudentRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.StudentResponse;
import com.kh.rupp_dev.boukryuniversity.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { StudentAddressMapper.class })
public interface StudentMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "clazz", ignore = true)
	@Mapping(target = "studentCode", ignore = true)
	@Mapping(target = "dateOfBirth", ignore = true)
	@Mapping(target = "enrollmentDate", ignore = true)
	@Mapping(target = "creationAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "status", ignore = true)
	@Mapping(target = "scores", ignore = true)
	Student toEntity(StudentRequest request);

	@Mapping(target = "address", source = "address")
	@Mapping(target = "classId" , source = "clazz.id")
	@Mapping(target = "className" , source = "clazz.name")
	StudentResponse toResponse(Student student);

	List<StudentResponse> toList(List<Student> students);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "clazz", ignore = true)
	@Mapping(target = "scores", ignore = true)
	@Mapping(target = "studentCode", ignore = true)
	@Mapping(target = "dateOfBirth", ignore = true)
	@Mapping(target = "enrollmentDate", ignore = true)
	@Mapping(target = "address", source = "address")
	@Mapping(target = "creationAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "status", ignore = true)
	void updateFromRequest(StudentRequest request, @MappingTarget Student student);

}
