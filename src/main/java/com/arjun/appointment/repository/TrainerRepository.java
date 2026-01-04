package com.arjun.appointment.repository;

import com.arjun.appointment.entity.Trainers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TrainerRepository extends JpaRepository<Trainers,Long> {
   List<Trainers> findByIsAvailable(Boolean isAvailable);
   List<Trainers> findByTrainerIdInAndIsAvailable(Set<Long> trainerIds, Boolean isAvailable);
}
