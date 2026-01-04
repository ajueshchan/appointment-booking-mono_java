package com.arjun.appointment.service.Iservice;

import com.arjun.appointment.dto.response.SlotDtoResponse;

import java.util.List;

public interface SlotService {
    String assignTrainersToSlots();
    List<SlotDtoResponse> getTrainerSlots();
}
