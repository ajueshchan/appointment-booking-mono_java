package com.arjun.appointment.service;

import com.arjun.appointment.dto.response.SlotDtoResponse;
import com.arjun.appointment.entity.Slot;
import com.arjun.appointment.entity.Trainers;
import com.arjun.appointment.repository.SlotRepository;
import com.arjun.appointment.repository.TrainerRepository;
import com.arjun.appointment.service.Iservice.SlotService;
import com.arjun.appointment.utils.DateUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.arjun.appointment.constant.AppointmentConstant.NO_TRAINER_FOR_SLOT;
import static com.arjun.appointment.constant.AppointmentConstant.PERSON_PER_SLOT;
import static com.arjun.appointment.constant.AppointmentConstant.SLOT_ASSIGNED_FOR_TRAINERS;
import static com.arjun.appointment.constant.AppointmentConstant.SLOT_TIME;
import static com.arjun.appointment.constant.AppointmentConstant.TRUE_AVAILABLE;

@Service
public class SlotServiceImpl implements SlotService {

    private final TrainerRepository trainerRepository;
    private final SlotRepository slotRepository;

    public SlotServiceImpl(TrainerRepository trainerRepository,
                           SlotRepository slotRepository){
        this.trainerRepository = trainerRepository;
        this.slotRepository = slotRepository;
    }

    @Override
    public String assignTrainersToSlots() {
        List<Trainers> availableTrainers = trainerRepository.findByIsAvailable(TRUE_AVAILABLE);

        if (availableTrainers.isEmpty()) {
            return NO_TRAINER_FOR_SLOT;
        }

        List<Trainers> groupAD = new ArrayList<>();
        List<Trainers> groupBC = new ArrayList<>();
        groupTrainersUponCategory(availableTrainers,groupAD,groupBC);
        int totalSlotsPerDay = SLOT_TIME.length / 2;
        List<Slot> persistToSlot = new ArrayList<>();
        for (int slotCount = 0; slotCount < totalSlotsPerDay; slotCount++) {
            int startIndex = slotCount * 2;
            int endIndex   = startIndex + 1;
            if((slotCount+1) % PERSON_PER_SLOT != 0){
                assignTrainersToSlot(groupAD,startIndex,endIndex,persistToSlot);
            }else{
                assignTrainersToSlot(groupBC,startIndex,endIndex,persistToSlot);
            }
        }
        slotRepository.saveAll(persistToSlot);
        return String.format("%s - %d slots created for %d trainers", SLOT_ASSIGNED_FOR_TRAINERS,
                persistToSlot.size(), availableTrainers.size());
    }

    @Override
    public List<SlotDtoResponse> getTrainerSlots() {
       List<Slot> availableSlots = slotRepository.findByIsAvailable(TRUE_AVAILABLE);
       Set<Long> trainerIds = availableSlots.stream().map(Slot::getTrainerId).collect(Collectors.toSet());
       List<Trainers> trainers = trainerRepository.findByTrainerIdInAndIsAvailable(trainerIds,TRUE_AVAILABLE);
       Map<Long,Trainers> trainersMap = new HashMap<>();
       if(!trainers.isEmpty()){
         trainersMap = trainers.stream().collect(Collectors.toMap(Trainers::getTrainerId, trainer->trainer));
       }
       List<SlotDtoResponse> slotDtoResponses = new ArrayList<>();
       for(int slotId=0;slotId<availableSlots.size();slotId++){
           Slot availableSlot = availableSlots.get(slotId);
           var slotResponse = new SlotDtoResponse();
           slotResponse.setSlotId(availableSlot.getSlotId());
           slotResponse.setTrainerId(availableSlot.getTrainerId());
           slotResponse.setTrainerName(trainersMap.get(availableSlot.getTrainerId()).getName());
           slotResponse.setTrainerCategory(trainersMap.get(availableSlot.getTrainerId()).getCategory());
           slotResponse.setSlotDate(availableSlot.getSlotDate());
           slotResponse.setSlotEndTime(DateUtil.formatHourAndMinuteTime(availableSlot.getSlotEndTime()));
           slotResponse.setSlotStartTime(DateUtil.formatHourAndMinuteTime(availableSlot.getSlotStartTime()));
           slotDtoResponses.add(slotResponse);
       }
       return slotDtoResponses;
    }

    private void assignTrainersToSlot(List<Trainers> groups,int startIndex, int endIndex,List<Slot> persistToSlot){
        for(Trainers trainer : groups){
            Slot slot = new Slot();
            slot.setSlotDate(LocalDate.now());
            slot.setSlotStartTime(LocalTime.of(SLOT_TIME[startIndex], 0));
            slot.setSlotEndTime(LocalTime.of(SLOT_TIME[endIndex], 0));
            slot.setTrainerId(trainer.getTrainerId());
            persistToSlot.add(slot);
        }
    }

    private void groupTrainersUponCategory(List<Trainers> availableTrainers,
                                           List<Trainers> groupAD,
                                           List<Trainers> groupBC) {
        for (Trainers trainer : availableTrainers) {
            switch (trainer.getCategory()) {
                case "A":
                case "D":
                    groupAD.add(trainer);
                    break;
                case "B":
                case "C":
                    groupBC.add(trainer);
                    break;
            }
        }
    }
}
