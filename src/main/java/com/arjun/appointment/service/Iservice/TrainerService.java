package com.arjun.appointment.service.Iservice;

import com.arjun.appointment.dto.request.TrainerDtoRequest;
import com.arjun.appointment.dto.response.Response;
import com.arjun.appointment.dto.response.TrainersDtoResponse;
import com.arjun.appointment.entity.Trainers;

import java.util.List;

public interface TrainerService {
    Response<List<Trainers>, List<TrainerDtoRequest>> persistTrainerDetails(List<TrainerDtoRequest> trainerDtoRequestList);
    Response<List<TrainersDtoResponse>,?> getAllTrainers();
}
