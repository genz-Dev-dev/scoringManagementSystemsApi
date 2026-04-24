package com.kh.rupp_dev.boukryuniversity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private UUID id;

    private String fullName;

    private String email;

    private boolean verified;

    private String verificationToken;

    private String refreshToken;

    private boolean status;

    private String role;

    private int attempt;

    private LocalDate lockTime;
}
