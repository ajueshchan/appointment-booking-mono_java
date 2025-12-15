package com.arjun.appointment.repository;

import com.arjun.appointment.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
