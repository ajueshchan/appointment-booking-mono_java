package com.arjun.appointment.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static com.arjun.appointment.constant.TokenConstant.FIRST_NAME;
import static com.arjun.appointment.constant.TokenConstant.LAST_NAME;
import static com.arjun.appointment.constant.TokenConstant.ROLE;
import static com.arjun.appointment.constant.TokenConstant.TOKEN_EXPIRED;
import static com.arjun.appointment.constant.TokenConstant.USERNAME_DELIMITER;

@Slf4j
@Component
public class JwtTokenExtraction {

    @Value("${jwt.secret}")
    private String secret;

    public boolean isTokenExpired(String token) {
        Claims claims = extractAllClaims(token);
        Date expirationDate = claims.getExpiration();
        return expirationDate.before(new Date());
    }

    private Claims extractAllClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }catch (ExpiredJwtException ex) {
            // Token expired, but claims are still accessible
            return ex.getClaims();
        }
        return claims;
    }

    public String extractUserName(String token){
        if(isTokenExpired(token)) return null;
        Claims claims = extractAllClaims(token);
        String userName = null;
        if(null != claims){
            if(null != claims.get(FIRST_NAME))
                userName = claims.get(FIRST_NAME).toString();
            if(null != claims.get(LAST_NAME))
                userName = userName + USERNAME_DELIMITER + claims.get(LAST_NAME).toString();
        }
        return userName;
    }

    public String extractUserRole(String token){
        if(isTokenExpired(token)) return TOKEN_EXPIRED;
        Claims claims = extractAllClaims(token);
        String userRole = null;
        if(null != claims){
            userRole = claims.get(ROLE).toString();
        }
        return userRole;
    }
}
