package com.kh.rupp_dev.boukryuniversity.mapper;

import com.kh.rupp_dev.boukryuniversity.dto.request.ScoreRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.ScoreResponse;
import com.kh.rupp_dev.boukryuniversity.entity.Score;
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
