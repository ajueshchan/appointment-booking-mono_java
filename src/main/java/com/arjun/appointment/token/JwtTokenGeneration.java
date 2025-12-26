package com.arjun.appointment.token;


import com.arjun.appointment.constant.ResponseConstant;
import com.arjun.appointment.entity.Users;
import com.arjun.appointment.repository.UsersRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class JwtTokenGeneration {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    @Autowired
    UsersRepository usersRepository;

    public String generateToken(String emailId){
        Users activeUser = usersRepository.findByEmail(emailId).orElse(null);
        if(Objects.nonNull(activeUser)){
            Map<String, Object> acceptableClaimInfoForTheEmailId = new HashMap<>();
            acceptableClaimInfoForTheEmailId.put("role", activeUser.getRole());
            acceptableClaimInfoForTheEmailId.put("userId", activeUser.getUserId());
            return createToken(acceptableClaimInfoForTheEmailId, activeUser.getEmail());
        }
        return ResponseConstant.NO_INFO;
    }

    private String createToken(Map<String, Object> acceptableClaimInfoForTheEmailId, String subject) {
        Date tokenRefreshDate = new Date();
        Date expiryDate = new Date(tokenRefreshDate.getTime() + expiration);
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .claims(acceptableClaimInfoForTheEmailId)
                .subject(subject)
                .issuedAt(tokenRefreshDate)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }
}
