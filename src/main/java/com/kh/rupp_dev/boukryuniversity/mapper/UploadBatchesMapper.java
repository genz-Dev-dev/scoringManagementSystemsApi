package com.kh.rupp_dev.boukryuniversity.mapper;

import com.kh.rupp_dev.boukryuniversity.dto.request.UploadBatchesRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.UploadBatchesResponse;
import com.kh.rupp_dev.boukryuniversity.entity.UploadBatches;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UploadBatchesMapper {

    @Mappings({
            @Mapping(target = "id" , ignore = true),
            @Mapping(target = "user" , ignore = true),
            @Mapping(target = "completedAt" , ignore = true),
            @Mapping(target = "createdAt" , ignore = true)
    })
    UploadBatches toEntity(UploadBatchesRequest request);

    @Mappings({
            @Mapping(target = "userId" , ignore = true),
            @Mapping(target = "username" , ignore = true)
    })
    UploadBatchesResponse toResponse(UploadBatches uploadBatches);

}
