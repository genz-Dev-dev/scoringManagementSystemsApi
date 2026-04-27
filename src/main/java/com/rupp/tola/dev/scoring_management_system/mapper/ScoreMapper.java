package com.rupp.tola.dev.scoring_management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.rupp.tola.dev.scoring_management_system.dto.request.ScoreRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ScoreResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Score;

@Mapper(componentModel = "spring")
public interface ScoreMapper {

    @Mapping(target = "student.id", source = "studentId")
    @Mapping(target = "subject.id", source = "subjectId")
    @Mapping(target = "semester.id", source = "semesterId")
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "status",source = "status")
    @Mapping(target = "version",source = "version")
    @Mapping(target = "score",source = "score")
    Score toEntity(ScoreRequest request);

    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "subjectId", source = "subject.id")
    @Mapping(target = "semesterId", source = "semester.id")
    @Mapping(target = "userId", source = "user.id")
    ScoreResponse toResponse(Score score);
}