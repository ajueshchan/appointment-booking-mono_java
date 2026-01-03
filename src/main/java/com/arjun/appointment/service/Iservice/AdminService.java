package com.arjun.appointment.service.Iservice;

import com.arjun.appointment.dto.request.UserDtoRequest;
import com.arjun.appointment.dto.response.UserDtoResponse;
import com.arjun.appointment.entity.Users;

import java.util.List;

public interface AdminService {
    List<Users> persistUserDetails(List<UserDtoRequest> userDtoRequest);
    List<UserDtoResponse> getAllUsers();
}
