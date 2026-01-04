package com.arjun.appointment.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SlotDtoResponse {
    private Long slotId;
    private Long trainerId;
    private String trainerName;
    private String trainerCategory;
    private LocalDate slotDate;
    private String slotStartTime;
    private String slotEndTime;
}
