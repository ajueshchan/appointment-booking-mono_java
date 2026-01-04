package com.arjun.appointment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerResponse<S,F>{
    private String status;
    private String message;
    private S savedData;
    private F failedData;

    public static <S,F> TrainerResponse<S,F> success(String message, S savedData) {
        return new TrainerResponse<>("SUCCESS", message, savedData, null);
    }

    public static <S,F> TrainerResponse<S,F> failure(String message, F failedData) {
        return new TrainerResponse<>("FAILURE", message, null, failedData);
    }

    public static <S,F> TrainerResponse<S,F> partial(String message, S savedData, F failedData) {
        return new TrainerResponse<>("PARTIAL", message, savedData, failedData);
    }
}
