package com.rupp.tola.dev.scoring_management_system.jwt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class JwtConfig {
    @Value("${jwt.secret}")
    private String base64Secret;
    @Value("${jwt.expiration-ms}")
    private long expiration;
}
