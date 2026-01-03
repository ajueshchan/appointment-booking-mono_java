package com.arjun.appointment.utils;

import com.arjun.appointment.dto.response.UserDtoResponse;

import java.util.function.Predicate;

public class Predicates {
    public static Predicate<UserDtoResponse> isUserEmailPredicate(String email) {
        return user -> user.getEmail() != null && user.getEmail().equals(email);
    }
}
