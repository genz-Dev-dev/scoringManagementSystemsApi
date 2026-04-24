package com.kh.rupp_dev.boukryuniversity.jwt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class JwtConfig {
    @Value("${jwt.secret}")
    private String base64Secret;
    @Value("${jwt.expiration-ms}")
    private long expiration;
}
