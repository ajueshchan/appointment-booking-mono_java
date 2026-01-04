package com.arjun.appointment.service;

import com.arjun.appointment.dto.request.TrainerDtoRequest;
import com.arjun.appointment.dto.response.Response;
import com.arjun.appointment.dto.response.TrainersDtoResponse;
import com.arjun.appointment.entity.Trainers;
import com.arjun.appointment.entity.Users;
import com.arjun.appointment.repository.TrainerRepository;
import com.arjun.appointment.repository.UsersRepository;
import com.arjun.appointment.service.Iservice.TrainerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Response<List<Trainers>, List<TrainerDtoRequest>> persistTrainerDetails(List<TrainerDtoRequest> trainerDtoRequestList) {

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
            return Response.failure("No Active MailIds",missingUserMail);
        }else if(!missingUserMail.isEmpty()){
            return Response.partial("No Active MailIds",savedTrainers,missingUserMail);
        }
        return Response.success("Data Saved Successfully", savedTrainers);
    }

    private Map<String,Users> validateTrainerRequest(List<TrainerDtoRequest> trainerDtoRequestList) {
      List<String> userMailIds = trainerDtoRequestList.stream().map(TrainerDtoRequest::getEmailId).toList();
      List<Users> activeUsers = usersRepository.findByEmailInAndStatus(userMailIds,"ACTIVE");
      return activeUsers.stream().collect(Collectors.toMap(Users::getEmail,user-> user));
    }

    @Transactional
    @Override
    public Response<List<TrainersDtoResponse>,?> getAllTrainers() {
       List<Trainers> trainers = trainerRepository.findAll();
       List<TrainersDtoResponse> trainersDtoResponses = trainers.stream().map(trainer -> {
           var trainerResp = new TrainersDtoResponse();
           BeanUtils.copyProperties(trainer,trainerResp);
           return trainerResp;
       }).toList();

        if(trainersDtoResponses.isEmpty()){
            return Response.failure("No Trainers in Gym",List.of());
        }
        return Response.success("Fetched Data Successfully", trainersDtoResponses);
    }
}
