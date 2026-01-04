package com.arjun.appointment.dto.response;

import lombok.Data;

@Data
public class TrainersDtoResponse {
    private Long trainerId;
    private String name;
    private String category;
    private Long salary;
    private Boolean isAvailable;
}
