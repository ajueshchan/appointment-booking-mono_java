package com.arjun.appointment.service;

import com.arjun.appointment.dto.request.UserDtoRequest;
import com.arjun.appointment.dto.response.UserDtoResponse;
import com.arjun.appointment.entity.Users;
import com.arjun.appointment.repository.UsersRepository;
import com.arjun.appointment.service.Iservice.AdminService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final UsersRepository usersRepository;

    public AdminServiceImpl(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    @Transactional
    @Override
    public List<Users> persistUserDetails(List<UserDtoRequest> userDtoRequests) {
        List<Users> users = new ArrayList<>();
        userDtoRequests.forEach(userDtoRequest -> {
            var user = new Users();
            BeanUtils.copyProperties(userDtoRequest,user);
            users.add(user);
        });
        return usersRepository.saveAll(users);
    }

    @Transactional
    @Override
    public List<UserDtoResponse> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        List<UserDtoResponse> userDtoResponseList = new ArrayList<>();
        users.forEach(user -> {
            var userDto = new UserDtoResponse();
            BeanUtils.copyProperties(user,userDto);
            userDtoResponseList.add(userDto);
        });
        return userDtoResponseList;
    }
}
