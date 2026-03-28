package com.rupp.tola.dev.scoring_management_system.mapper;

import com.rupp.tola.dev.scoring_management_system.dto.request.ScoreRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ScoreResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Score;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ScoreMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "student.id", source = "studentId"),
            @Mapping(target = "course.courseId.semesterId", source = "semesterId"),
            @Mapping(target = "course.courseId.subjectId", source = "subjectId")
    })
    Score toEntity(ScoreRequest request);

    @Mappings({
            @Mapping(target = "studentId", source = "student.id"),
            @Mapping(target = "semesterId", source = "course.courseId.semesterId"),
            @Mapping(target = "subjectId", source = "course.courseId.subjectId")
    })
    ScoreResponse toResponse(Score score);

}