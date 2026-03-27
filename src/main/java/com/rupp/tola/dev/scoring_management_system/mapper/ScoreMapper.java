package com.rupp.tola.dev.scoring_management_system.mapper;

import com.rupp.tola.dev.scoring_management_system.dto.request.ScoreRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ScoreResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Score;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScoreMapper {

    @Mapping(source = "studentId", target = "student.id")
//    @Mapping(source = "subjectId", target = "subject.id")
//    @Mapping(source = "semesterId", target = "semester.id")
    Score toEntity(ScoreRequest request);

    @Mapping(source = "student.id", target = "studentId")
//    @Mapping(source = "subject.id", target = "subjectId")
//    @Mapping(source = "semester.id", target = "semesterId")
    ScoreResponse toDetailResponse(Score score);

}
