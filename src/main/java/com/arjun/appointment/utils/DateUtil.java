package com.arjun.appointment.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
   public static DateTimeFormatter HOUR_MINUTE_PATTERN = DateTimeFormatter.ofPattern("ha");

    public static String formatHourAndMinuteTime(LocalTime time) {
        return time.format(HOUR_MINUTE_PATTERN).toLowerCase();
    }
}
