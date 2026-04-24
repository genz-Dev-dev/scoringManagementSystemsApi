package com.kh.rupp_dev.boukryuniversity.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionRequest {

    @NotBlank(message = "Name is required.")
    @Size(min = 2 , max = 25 , message = "Name must be between 2 and 50 characters.")
    private String name;

    @NotBlank(message = "Description is required.")
    @Size(min = 5 , max = 100 , message = "Description must be between 5 and 100 characters.")
    private String description;

    @NotBlank(message = "Module is required.")
    @Size(min = 2 , max = 25 , message = "Module must be between t and 25 characters.")
    private String module;

    private Set<UUID> roleIds;
}
