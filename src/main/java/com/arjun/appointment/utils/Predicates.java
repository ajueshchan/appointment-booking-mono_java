package com.arjun.appointment.utils;

import com.arjun.appointment.dto.request.TrainerDtoRequest;
import com.arjun.appointment.dto.response.UserDtoResponse;

import java.util.Set;
import java.util.function.Predicate;

public class Predicates {
    public static Predicate<UserDtoResponse> isUserEmailPredicate(String email) {
        return user -> user.getEmail() != null && user.getEmail().equals(email);
    }

    public static Predicate<TrainerDtoRequest> isActiveUserPredicate(Set<String> emailIds) {
        return trainerDtoRequest -> trainerDtoRequest.getEmailId() != null && emailIds.contains(trainerDtoRequest.getEmailId());
    }

    public static Predicate<TrainerDtoRequest> isInActiveUserPredicate(Set<String> emailIds) {
        return trainerDtoRequest -> trainerDtoRequest.getEmailId() != null && !emailIds.contains(trainerDtoRequest.getEmailId());
    }
}
