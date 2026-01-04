package com.arjun.appointment.repository;

import com.arjun.appointment.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {
    List<Users> findByEmailInAndStatus(List<String> emailIds,String status);
}
