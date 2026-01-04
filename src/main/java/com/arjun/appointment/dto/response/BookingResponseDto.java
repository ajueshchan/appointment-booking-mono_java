package com.arjun.appointment.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingResponseDto {
    private Long bookingId;
    private Long userId;
    private Long trainerId;
    private LocalDate bookingDate;
    private String slotStartTime;
    private String slotEndTime;
    private String bookingStatus;
}
