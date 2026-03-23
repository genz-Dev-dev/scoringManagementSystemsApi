package com.rupp.tola.dev.scoring_management_system.jwt.impl;

import com.rupp.tola.dev.scoring_management_system.jwt.JwtConfig;
import com.rupp.tola.dev.scoring_management_system.jwt.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtServiceImpl extends JwtConfig implements JwtService {

    @Override
    public SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(getBase64Secret().getBytes());
    }

    @Override
    public String generateToken(String subject) {
        return generateToken(new HashMap<>(), subject);
    }

    @Override
    public String generateToken(Map<String, Object> claims, String subject) {
        return builderToken(claims , subject , getExpiration());
    }

    @Override
    public String extractEmail(String token) {
        return extractClaims(token , Claims::getSubject);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userEmail = extractEmail(token);
        return (userDetails.getUsername().equals(userEmail) && !isTokenExpiration(token));
    }

    public boolean isTokenExpiration(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    private <T> T extractClaims(String token , Function<Claims , T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }catch (MalformedJwtException ex) {
            log.info("Invalid format JWT token.");
            throw new JwtException("Invalid format JWT token.");
        }catch (ExpiredJwtException ex) {
            log.info("JWT token is expiration.");
            throw new JwtException("JWT token is expiration.");
        }catch (UnsupportedJwtException ex) {
            log.info("UnSupport JWT token.");
            throw new JwtException("UnSupport JWT token.");
        }catch (ClaimJwtException ex) {
            log.info(ex.getMessage());
            throw new JwtException(ex.getMessage());
        } catch (JwtException ex) {
            log.info("Can not extract JWT token");
            throw new JwtException("Can not extract JWT token.");
        }
    }

    private String builderToken(Map<String , Object> claims , String subject , long expiration) {
        return Jwts
                .builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

}
