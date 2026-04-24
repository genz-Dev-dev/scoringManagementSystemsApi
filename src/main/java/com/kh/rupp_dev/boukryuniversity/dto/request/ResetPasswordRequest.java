package com.kh.rupp_dev.boukryuniversity.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordRequest {

    @NotBlank(message = "Password is required.")
    @Size(min = 5 , message = "Password must be 5 characters up.")
    private String password;

}
