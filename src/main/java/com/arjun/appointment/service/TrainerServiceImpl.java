package com.arjun.appointment.service;

import com.arjun.appointment.dto.request.TrainerDtoRequest;
import com.arjun.appointment.dto.response.TrainerResponse;
import com.arjun.appointment.dto.response.SlotDtoResponse;
import com.arjun.appointment.dto.response.TrainersDtoResponse;
import com.arjun.appointment.entity.Slot;
import com.arjun.appointment.entity.Trainers;
import com.arjun.appointment.entity.Users;
import com.arjun.appointment.repository.TrainerRepository;
import com.arjun.appointment.repository.UsersRepository;
import com.arjun.appointment.service.Iservice.TrainerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.arjun.appointment.constant.AppointmentConstant.ACTIVE_STATUS;
import static com.arjun.appointment.constant.AppointmentConstant.FAILURE_FETCH_TRAINER;
import static com.arjun.appointment.constant.AppointmentConstant.PERSON_PER_SLOT;
import static com.arjun.appointment.constant.AppointmentConstant.SLOT_TIME;
import static com.arjun.appointment.constant.AppointmentConstant.SUCCESS_FETCH_TRAINER;
import static com.arjun.appointment.constant.AppointmentConstant.SUCCESS_PERSIST_TRAINER;
import static com.arjun.appointment.constant.AppointmentConstant.UNREGISTERED_USER;
import static com.arjun.appointment.utils.Predicates.isActiveUserPredicate;
import static com.arjun.appointment.utils.Predicates.isInActiveUserPredicate;

@Service
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final UsersRepository usersRepository;

    public TrainerServiceImpl(TrainerRepository trainerRepository,
                              UsersRepository usersRepository){
        this.trainerRepository = trainerRepository;
        this.usersRepository = usersRepository;
    }

    @Transactional
    @Override
    public TrainerResponse<List<Trainers>, List<TrainerDtoRequest>> persistTrainerDetails(List<TrainerDtoRequest> trainerDtoRequestList) {

        Map<String,Users> activeUsersMap = validateTrainerRequest(trainerDtoRequestList);
        Set<String> activeUserMail = activeUsersMap.keySet();
        List<Trainers> trainersToBePersisted = trainerDtoRequestList.stream().filter(isActiveUserPredicate(activeUserMail))
                .map(trainerDtoRequest -> {
                    var trainer = new Trainers();
                    BeanUtils.copyProperties(trainerDtoRequest,trainer);
                    Users user = activeUsersMap.get(trainerDtoRequest.getEmailId());
                    trainer.setUserId(null != user ? user.getUserId() : 1L);
                    return trainer;
        }).toList();
       List<Trainers> savedTrainers = trainerRepository.saveAll(trainersToBePersisted);

       List<TrainerDtoRequest> missingUserMail = trainerDtoRequestList.stream()
               .filter(isInActiveUserPredicate(activeUserMail)).toList();

        if(trainersToBePersisted.isEmpty()){
            return TrainerResponse.failure(UNREGISTERED_USER,missingUserMail);
        }else if(!missingUserMail.isEmpty()){
            return TrainerResponse.partial(UNREGISTERED_USER,savedTrainers,missingUserMail);
        }
        return TrainerResponse.success(SUCCESS_PERSIST_TRAINER, savedTrainers);
    }

    private Map<String,Users> validateTrainerRequest(List<TrainerDtoRequest> trainerDtoRequestList) {
      List<String> userMailIds = trainerDtoRequestList.stream().map(TrainerDtoRequest::getEmailId).toList();
      List<Users> activeUsers = usersRepository.findByEmailInAndStatus(userMailIds, ACTIVE_STATUS);
      return activeUsers.stream().collect(Collectors.toMap(Users::getEmail,user-> user));
    }

    @Transactional
    @Override
    public TrainerResponse<List<TrainersDtoResponse>,?> getAllTrainers() {
       List<Trainers> trainers = trainerRepository.findAll();
       List<TrainersDtoResponse> trainersDtoResponses = trainers.stream().map(trainer -> {
           var trainerResp = new TrainersDtoResponse();
           BeanUtils.copyProperties(trainer,trainerResp);
           return trainerResp;
       }).toList();

        if(trainersDtoResponses.isEmpty()){
            return TrainerResponse.failure(FAILURE_FETCH_TRAINER,List.of());
        }
        return TrainerResponse.success(SUCCESS_FETCH_TRAINER, trainersDtoResponses);
    }
}
