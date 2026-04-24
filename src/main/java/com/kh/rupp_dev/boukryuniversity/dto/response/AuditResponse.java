package com.kh.rupp_dev.boukryuniversity.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditResponse {

    private UUID id;

    private String username;

    private String action;

    private String entityName;

    private String entityId;

    private String oldValue;

    private String newValue;

    private LocalDateTime timestamp;

}
