package com.arjun.appointment.controller;

import com.arjun.appointment.dto.request.UserDtoRequest;
import com.arjun.appointment.dto.response.UserDtoResponse;
import com.arjun.appointment.entity.Users;
import com.arjun.appointment.service.Iservice.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.arjun.appointment.constant.TokenConstant.ROLE_ADMIN;


@Slf4j
@RestController
@RequestMapping("/public")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PreAuthorize(ROLE_ADMIN)
    @GetMapping(path = "/users/get", name = "Get All the Users List from DB")
    public List<UserDtoResponse> getUser(){
       return adminService.getAllUsers();
    }

    @PreAuthorize(ROLE_ADMIN)
    @PostMapping(path = "/users/add", name = "Add Single User from Token to DB")
    public List<Users> addUser(@RequestBody List<UserDtoRequest> userDtoRequest){
        return adminService.persistUserDetails(userDtoRequest);
    }
}
