package com.arjun.appointment.controller;

import com.arjun.appointment.dto.UserDto;
import com.arjun.appointment.entity.UserRole;
import com.arjun.appointment.entity.UserStatus;
import com.arjun.appointment.entity.Users;
import com.arjun.appointment.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UsersRepository usersRepository;

    public UserController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/add")
    @Transactional
    public void addUser(){
       UserDto userDto = UserDto.builder().email("nagarjunjeyachandran@gmail.com").phone("9943397075")
               .password("test").role(UserRole.ADMIN.name()).status(UserStatus.ACTIVE.name())
               .firstName("Nagarjun").lastName("jeyachandran").build();
       var user = new Users();
       BeanUtils.copyProperties(userDto,user);
       Users users = usersRepository.saveAndFlush(user);
       log.info("New User is added Id: {}",users.getUserId());
    }

    @GetMapping("/get")
    @Transactional
    public List<UserDto> getUser(){
        List<Users> users = usersRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        users.forEach(user -> {
            var userDto = new UserDto();
            BeanUtils.copyProperties(user,userDto);
            userDtoList.add(userDto);
        });
        return userDtoList;
    }
}
