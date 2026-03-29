package com.rupp.tola.dev.scoring_management_system.mapper;

import com.rupp.tola.dev.scoring_management_system.dto.request.ScoreRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ScoreResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Score;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface ScoreMapper {

    @Mapping(source = "student" , target = "student.id")
    @Mapping(source = "semesterId", target = "course.courseId.semesterId")
    @Mapping(source = "subjectId" , target = "course.courseId.subjectId")
    @Mapping(source = "score", target = "score")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "grade", ignore = true)
    @Mapping(target = "creationAt", ignore = true)
    @Mapping(target = "UpdatedAt",ignore = true)
    Score toEntity(ScoreRequest request);

    @Mapping(source = "student.id", target = "student")
    @Mapping(source = "course.courseId.semesterId", target = "semesterId")
    @Mapping(source = "course.courseId.subjectId",  target = "subjectId")
    @Mapping(source = "course.credit",        target = "credit")
    @Mapping(source = "studentId",            target = "studentId")
    @Mapping(source = "score",                target = "score")
    @Mapping(source = "grade",                target = "grade")
    @Mapping(source = "creationAt",            target = "creationAt")
    @Mapping(source = "updatedAt",            target = "updatedAt")
    ScoreResponse toResponse(Score score);

    List<ScoreResponse> toResponseList(List<Score> scores);

    @Mapping(source = "student" , target = "student.id")
    @Mapping(source = "semesterId", target = "course.courseId.semesterId")
    @Mapping(source = "subjectId" , target = "course.courseId.subjectId")
    @Mapping(source = "score", target = "score")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "grade", ignore = true)
    @Mapping(target = "creationAt", ignore = true)
    @Mapping(target = "UpdatedAt",ignore = true)
    void updateFromRequest(ScoreRequest request, @MappingTarget Score score);

}