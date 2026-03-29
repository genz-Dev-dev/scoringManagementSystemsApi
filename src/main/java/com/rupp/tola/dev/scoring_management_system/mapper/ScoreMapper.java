package com.rupp.tola.dev.scoring_management_system.mapper;

import com.rupp.tola.dev.scoring_management_system.dto.request.ScoreRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ScoreResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Score;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScoreMapper {

    @Mapping(target = "student", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Score toEntity(ScoreRequest request);

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "course.courseId.semesterId", target = "semesterId")
    @Mapping(source = "course.courseId.subjectId", target = "subjectId")
    ScoreResponse toResponse(Score score);

    List<ScoreResponse> toResponseList(List<Score> scores);

    @Mapping(target = "student", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "creationAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromRequest(ScoreRequest request, @MappingTarget Score score);

}
