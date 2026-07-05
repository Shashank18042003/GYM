package com.gym_membership.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.gym_membership.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private long jwtExpriy;
	
	// Generating jwt Token
	
	public String generateToken(User userDetails) {
		Map<String,Object> claims =new HashMap<>();
		
		claims.put("userId",userDetails.getId());
		claims.put("role", userDetails.getRole().name());
		return buildToken(claims, userDetails);
	}
		
		
	
	// build token
		
	private String buildToken(
			Map<String ,Object> claims,User userDetails) {
		return Jwts.builder()
				.claims(claims)
				.subject(userDetails.getEmail())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis()+jwtExpriy))
				.signWith(getSigningKey())
				.compact();
	}
	
	public Long extractUserId(String token) {
	    return extractClaim(token, claims -> claims.get("userId", Long.class));
	}
	
	//extract username
	public String extractEmail(String token) {
		return extractClaim(token,Claims::getSubject);
	}
	
	public String extractRole(String token) {
	    return extractClaim(token, claims -> claims.get("role", String.class));
	}
	
	//extract any claim
	public <T> T extractClaim(String token,Function<Claims,T> resolver) {
		Claims claims=extractAllClaims(token);
		return resolver.apply(claims);
	}
	
	//extract claims
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	//validate token
	
	public boolean isTokenValid(String token,UserDetails userDetails) {
		String email=extractEmail(token);
		return email.equals(userDetails.getUsername())
				&& !isTokenExpired(token);
	}
	
	//check Expiry
	
	private boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration)
				.before(new Date());

	}
	
	private SecretKey getSigningKey() {
		byte[] keyBytes=Decoders.BASE64.decode(secretKey);
		
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
