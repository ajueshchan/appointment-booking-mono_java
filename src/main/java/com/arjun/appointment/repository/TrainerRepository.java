package com.arjun.appointment.repository;

import com.arjun.appointment.entity.Trainers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainers,Long> {
}
