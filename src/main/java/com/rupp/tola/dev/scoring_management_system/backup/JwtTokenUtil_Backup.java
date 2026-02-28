package com.rupp.tola.dev.scoring_management_system.backup;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil_Backup {

	private static final long EXPIRATION_MS = 24 * 60 * 60 * 1000L; // 24 hours

	@Value("${jwt.secret}")
	private String secret;

	public String generateToken(String email) {
		return Jwts.builder().setSubject(email).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

//	private String tokenBuilder(Map<String , Object> claims , String subject , long expiration) {
//		return Jwts
//				.builder()
//				.setSubject()
//				.addClaims()
//				.setIssuedAt()
//				.compact();
//	}

	public String extractEmail(String token) {
		return parseClaims(token).getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Claims claims = parseClaims(token);
			return !claims.getExpiration().before(new Date());
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	// ------------------------------------------------------------------ private

	private Claims parseClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}

	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}
}