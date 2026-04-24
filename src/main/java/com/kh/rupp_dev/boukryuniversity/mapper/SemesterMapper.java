package com.kh.rupp_dev.boukryuniversity.mapper;

import com.kh.rupp_dev.boukryuniversity.dto.request.SemesterRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.SemesterResponse;
import com.kh.rupp_dev.boukryuniversity.entity.Semester;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SemesterMapper {

    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "startDate" , ignore = true)
    @Mapping(target = "endDate" , ignore = true)
    Semester toEntity(SemesterRequest request);

    SemesterResponse toResponse(Semester semester);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "startDate" , ignore = true)
    @Mapping(target = "endDate" , ignore = true)
    void updateFromRequest(SemesterRequest request, @MappingTarget Semester semester);
}
