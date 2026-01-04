package com.arjun.appointment.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrainersDtoResponse {
    private Long trainerId;
    private String name;
    private String category;
    private Long salary;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isAvailable;
}
