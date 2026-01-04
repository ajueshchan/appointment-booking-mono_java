package com.arjun.appointment.repository;

import com.arjun.appointment.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SlotRepository extends JpaRepository<Slot,Long> {
    List<Slot> findByIsAvailable(Boolean isAvailable);
    Optional<Slot> findBySlotIdAndIsAvailable(Long slotId, Boolean isAvailable);
}
