package com.kh.rupp_dev.boukryuniversity.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.rupp_dev.boukryuniversity.dto.response.AuditLogResponse;
import com.kh.rupp_dev.boukryuniversity.entity.AuditLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface AuditLogMapper {

    @Mapping(target = "recordId", expression = "java(auditLog.getRecordId() != null ? auditLog.getRecordId().toString() : null)")
    @Mapping(target = "oldData", expression = "java(jsonToMap(auditLog.getOldData()))")
    @Mapping(target = "newData", expression = "java(jsonToMap(auditLog.getNewData()))")
    AuditLogResponse toResponse(AuditLog auditLog);

    // Convert JSON string → Map
    default Map<String, Object> jsonToMap(String json) {
        try {
            if (json == null) return null;
            return 
                new ObjectMapper()
                    .readValue(json, Map.class);
                    
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
}