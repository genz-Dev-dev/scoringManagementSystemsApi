package com.rupp.tola.dev.scoring_management_system.mapper;

import com.rupp.tola.dev.scoring_management_system.dto.response.StudentScoreResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Score;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentScoreMapper {
    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "student.khFirstName", target = "khFirstName")
    @Mapping(source = "student.khLastName", target = "khLastName")
    @Mapping(source = "student.enFirstName", target = "enFirstName")
    @Mapping(source = "student.enLastName", target = "enLastName")
    @Mapping(source = "student.gender", target = "gender")
    @Mapping(source = "student.studentCode", target = "studentCode")

    @Mapping(source = "student.clazz.name", target = "className")
    @Mapping(source = "subject.name", target = "subjectName")
    @Mapping(source = "semester.name", target = "semesterName")

    @Mapping(source = "score", target = "score")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "creationAt", target = "creationAt")
    @Mapping(source = "updatedAt", target = "updatedAt")

    StudentScoreResponse toDto(Score score);

    List<StudentScoreResponse> toDtoList(List<Score> scores);
}
