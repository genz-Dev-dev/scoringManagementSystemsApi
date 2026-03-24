package com.rupp.tola.dev.scoring_management_system.mapper;

import com.rupp.tola.dev.scoring_management_system.dto.request.SubjectRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.SubjectResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper( componentModel = "spring")
public interface SubjectMapper {

    Subject toEntity(SubjectRequest request);

    @Mapping(target = "scores", source = "scores")
    SubjectResponse toResponse(Subject subject);
}
