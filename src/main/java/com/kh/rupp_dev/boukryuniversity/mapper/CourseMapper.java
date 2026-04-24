package com.kh.rupp_dev.boukryuniversity.mapper;

import com.kh.rupp_dev.boukryuniversity.dto.request.CourseRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.CourseResponse;
import com.kh.rupp_dev.boukryuniversity.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    
    @Mappings({
            @Mapping(target = "courseId.semesterId" , source = "semesterId"),
            @Mapping(target = "courseId.subjectId" , source = "subjectId"),
            @Mapping(target = "instructor" , ignore = true),
            @Mapping(target = "schedules" , ignore = true),
            @Mapping(target = "startAt" , ignore = true),
            @Mapping(target = "endAt" , ignore = true),
    })
    Course toEntity(CourseRequest request);

    @Mappings({
            @Mapping(target = "semesterId" , source = "courseId.semesterId"),
            @Mapping(target = "semesterName" , ignore = true),
            @Mapping(target = "subjectId" , source = "courseId.subjectId"),
            @Mapping(target = "subjectName" , ignore = true),
            @Mapping(target = "instructorId" , source = "instructor.id"),
            @Mapping(target = "instructorName" , source = "instructor.fullName"),
            @Mapping(target = "schedule" , ignore = true),
            @Mapping(target = "schedules" , ignore = true),
    })
    CourseResponse toResponse(Course entity);

    @Mappings({
            @Mapping(target = "courseId", ignore = true),
            @Mapping(target = "instructor", ignore = true),
            @Mapping(target = "schedules", ignore = true),
            @Mapping(target = "startAt", ignore = true),
            @Mapping(target = "endAt", ignore = true),
    })
    void updateFromRequest(CourseRequest request, @MappingTarget Course course);

    List<CourseResponse> toList(List<Course> courses);
}
