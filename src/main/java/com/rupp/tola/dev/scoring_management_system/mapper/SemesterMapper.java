package com.rupp.tola.dev.scoring_management_system.mapper;

import com.rupp.tola.dev.scoring_management_system.dto.request.SemesterRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.SemesterResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Semester;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SemesterMapper {

    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "startAt" , ignore = true)
    @Mapping(target = "endAt" , ignore = true)
    Semester toEntity(SemesterRequest request);

    SemesterResponse toResponse(Semester semester);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "startAt" , ignore = true)
    @Mapping(target = "endAt" , ignore = true)
    void updateFromRequest(SemesterRequest request, @MappingTarget Semester semester);


}
