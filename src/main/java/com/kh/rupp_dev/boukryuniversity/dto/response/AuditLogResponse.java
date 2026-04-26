package com.kh.rupp_dev.boukryuniversity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditLogResponse {

    private UUID id;

    private String action;

    private LocalDateTime changeAt;

    private String recordId;

    private String tableName;

    private Map<String,Object> oldData;

    private Map<String,Object> newData;

}
