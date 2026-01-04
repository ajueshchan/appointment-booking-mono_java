package com.arjun.appointment.dto.request;

import lombok.Data;

@Data
public class TrainerDtoRequest {
    private String emailId;
    private String name;
    private String category;
    private Long salary;
}
