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
import java.util.Arrays;
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

    @GetMapping("/bulkAdd")
    @Transactional
    public List<Users> addingUserBulk(){
        UserDto user1 = new UserDto();
        user1.setId(1L);
        user1.setEmail("john.admin@example.com");
        user1.setFirstName("John");
        user1.setLastName("Admin");
        user1.setPhone("9876543210");
        user1.setPassword("p1");
        user1.setRole(UserRole.TRAINER.name());
        user1.setStatus(UserStatus.ACTIVE.name());

        UserDto user2 = new UserDto();
        user2.setId(2L);
        user2.setEmail("jane.customer@example.com");
        user2.setFirstName("Jane");
        user2.setLastName("Customer");
        user2.setPhone("9123456789");
        user2.setPassword("p2");
        user2.setRole(UserRole.CUSTOMER.name());
        user2.setStatus(UserStatus.ACTIVE.name());

        // Use in a list
        List<UserDto> users = Arrays.asList(user1, user2);
        List<Users> userEntities = new ArrayList<>();
        users.forEach(userDto -> {
            Users userTest = new Users();
            BeanUtils.copyProperties(userDto,userTest);
            userEntities.add(userTest);
        });
        return usersRepository.saveAllAndFlush(userEntities);
    }
}
