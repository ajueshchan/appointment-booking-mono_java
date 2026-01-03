package com.arjun.appointment.security.token;


import com.arjun.appointment.AppointmentBookingApplication;
import com.arjun.appointment.constant.ResponseConstant;
import com.arjun.appointment.dto.response.UserDtoResponse;
import com.arjun.appointment.repository.UsersRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.crypto.SecretKey;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.arjun.appointment.constant.TokenConstant.EMAIL;
import static com.arjun.appointment.constant.TokenConstant.FIRST_NAME;
import static com.arjun.appointment.constant.TokenConstant.LAST_NAME;
import static com.arjun.appointment.constant.TokenConstant.PHONE;
import static com.arjun.appointment.constant.TokenConstant.ROLE;
import static com.arjun.appointment.utils.Predicates.isUserEmailPredicate;

@Slf4j
@Component
public class JwtTokenGeneration {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    @Autowired
    UsersRepository usersRepository;

    public String generateToken(String emailId)  {
        List<UserDtoResponse> user = new ArrayList<>();
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            InputStream is = AppointmentBookingApplication.class.getClassLoader()
                    .getResourceAsStream("static/Users.json");

            TypeReference<List<UserDtoResponse>> userListReference = new TypeReference<>() {};
            List<UserDtoResponse> users = objectMapper.readValue(is,userListReference);
            user = users.stream().filter(isUserEmailPredicate(emailId)).toList();
        }catch (Exception exec){
            log.error("Exception caused by",exec);
        }

        if(!CollectionUtils.isEmpty(user)){
            UserDtoResponse fetchUser = user.getFirst();
            Map<String, Object> acceptableClaimInfoForTheEmailId = new HashMap<>();
            acceptableClaimInfoForTheEmailId.put(ROLE, fetchUser.getRole());
            acceptableClaimInfoForTheEmailId.put(EMAIL,fetchUser.getEmail());
            acceptableClaimInfoForTheEmailId.put(FIRST_NAME,fetchUser.getFirstName());
            acceptableClaimInfoForTheEmailId.put(LAST_NAME,fetchUser.getLastName());
            acceptableClaimInfoForTheEmailId.put(PHONE,fetchUser.getPhone());
            return createToken(acceptableClaimInfoForTheEmailId, fetchUser.getEmail());
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
