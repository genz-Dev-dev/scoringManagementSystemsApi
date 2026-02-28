package com.rupp.tola.dev.scoring_management_system.jwt;

import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Map;

public interface JwtService {

    Key getSigningKey();
    String generateToken(String subject);
    String generateToken(Map<String , Object> claims , String subject);
    String extractEmail(String token);
    boolean isTokenExpiration(String token);
    boolean isTokenValid(String token , UserDetails userDetails);
}
