package com.arjun.appointment.controller;

import com.arjun.appointment.dto.request.TrainerDtoRequest;
import com.arjun.appointment.dto.request.UserDtoRequest;
import com.arjun.appointment.dto.response.SlotDtoResponse;
import com.arjun.appointment.dto.response.TrainerResponse;
import com.arjun.appointment.dto.response.TrainersDtoResponse;
import com.arjun.appointment.dto.response.UserDtoResponse;
import com.arjun.appointment.entity.Trainers;
import com.arjun.appointment.entity.Users;
import com.arjun.appointment.service.Iservice.SlotService;
import com.arjun.appointment.service.Iservice.TrainerService;
import com.arjun.appointment.service.Iservice.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.arjun.appointment.constant.TokenConstant.ROLE_ADMIN;
import static com.arjun.appointment.constant.TokenConstant.ROLE_ADMIN_TRAINER;


@Slf4j
@RestController
@RequestMapping("/public/gym")
public class AdminController {

    private final UsersService usersService;
    private final TrainerService trainerService;
    private final SlotService slotService;

    public AdminController(UsersService usersService,
                           TrainerService trainerService,
                           SlotService slotService) {
        this.usersService = usersService;
        this.trainerService = trainerService;
        this.slotService = slotService;
    }

    @PreAuthorize(ROLE_ADMIN)
    @GetMapping(path = "/users/get", name = "Get All the Users List from DB")
    public List<UserDtoResponse> getUser(){
       return usersService.getAllUsers();
    }

    @PreAuthorize(ROLE_ADMIN)
    @PostMapping(path = "/users/add", name = "Persist users to DB")
    public List<Users> addUser(@RequestBody List<UserDtoRequest> userDtoRequest){
        return usersService.persistUserDetails(userDtoRequest);
    }

    @PreAuthorize(ROLE_ADMIN_TRAINER)
    @GetMapping(path = "/trainers/get", name = "Get All the Trainers List from DB")
    public TrainerResponse<List<TrainersDtoResponse>, ?> getTrainers(){
        return trainerService.getAllTrainers();
    }

    @PreAuthorize(ROLE_ADMIN_TRAINER)
    @PostMapping(path = "/trainers/add", name = "Persist Trainers to DB")
    public TrainerResponse<List<Trainers>, List<TrainerDtoRequest>> addTrainers(@RequestBody List<TrainerDtoRequest> trainerDtoRequest){
        return trainerService.persistTrainerDetails(trainerDtoRequest);
    }

    @PreAuthorize(ROLE_ADMIN)
    @GetMapping(path = "/slot/assign", name = "Assign trainers to slots")
    public String trainerSlotsAssignment(){
        return slotService.assignTrainersToSlots();
    }

    @PreAuthorize(ROLE_ADMIN)
    @GetMapping(path = "/slot/get", name = "Fetch trainers slots")
    public List<SlotDtoResponse> getTrainerSlots(){
        return slotService.getTrainerSlots();
    }
}
