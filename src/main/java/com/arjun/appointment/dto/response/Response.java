package com.arjun.appointment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response <S,F>{
    private String status;
    private String message;
    private S savedData;
    private F failedData;

    public static <S,F> Response<S,F> success(String message,S savedData) {
        return new Response<>("SUCCESS", message, savedData, null);
    }

    public static <S,F> Response<S,F> failure(String message,F failedData) {
        return new Response<>("FAILURE", message, null, failedData);
    }

    public static <S,F> Response<S,F> partial(String message, S savedData, F failedData) {
        return new Response<>("PARTIAL", message, savedData, failedData);
    }
}
