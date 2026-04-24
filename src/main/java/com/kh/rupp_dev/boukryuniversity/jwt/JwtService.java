package com.kh.rupp_dev.boukryuniversity.jwt;

import java.security.Key;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    Key getSigningKey();

    String generateToken(String subject);

    String generateToken(Map<String , Object> claims , String subject);

    String extractEmail(String token);

    boolean isTokenExpiration(String token);

    boolean isTokenValid(String token , UserDetails userDetails);

}
