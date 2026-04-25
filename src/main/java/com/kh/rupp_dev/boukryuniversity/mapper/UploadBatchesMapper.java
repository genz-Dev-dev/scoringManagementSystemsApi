package com.kh.rupp_dev.boukryuniversity.mapper;

import com.kh.rupp_dev.boukryuniversity.dto.response.UploadBatchesResponse;
import com.kh.rupp_dev.boukryuniversity.entity.UploadBatches;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UploadBatchesMapper {

    UploadBatchesResponse toResponse(UploadBatches uploadBatches);

}
