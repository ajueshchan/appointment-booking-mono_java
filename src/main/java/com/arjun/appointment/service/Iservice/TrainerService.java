package com.arjun.appointment.service.Iservice;

import com.arjun.appointment.dto.request.TrainerDtoRequest;
import com.arjun.appointment.dto.response.TrainerResponse;
import com.arjun.appointment.dto.response.SlotDtoResponse;
import com.arjun.appointment.dto.response.TrainersDtoResponse;
import com.arjun.appointment.entity.Trainers;

import java.util.List;

public interface TrainerService {
    TrainerResponse<List<Trainers>, List<TrainerDtoRequest>> persistTrainerDetails(List<TrainerDtoRequest> trainerDtoRequestList);
    TrainerResponse<List<TrainersDtoResponse>,?> getAllTrainers();
}
