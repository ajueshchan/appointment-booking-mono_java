package com.arjun.appointment.dto.request;

import lombok.Data;

@Data
public class BookingRequestDto {
    private Long userId;
    private Long trainerId;
    private Long slotId;
}
