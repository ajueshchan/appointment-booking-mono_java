package com.arjun.appointment.constant;

public class AppointmentConstant {
    public static final String NO_INFO = "No Information Available For the emailId";
    public static final Boolean TRUE_AVAILABLE = true;
    public static final Boolean FALSE_AVAILABLE = false;
    public static final String ACTIVE_STATUS = "ACTIVE";
    public static final String SUCCESS_PERSIST_TRAINER = "Trainer Saved Successfully";
    public static final String SUCCESS_FETCH_TRAINER = "Fetched Trainer Successfully";
    public static final String FAILURE_FETCH_TRAINER = "No Trainers in Gym";
    public static final String UNREGISTERED_USER = "Un Registered User found";
    public static final String SLOT_ASSIGNED_FOR_TRAINERS = "Slot Assigned Successfully";
    public static final String NO_TRAINER_FOR_SLOT = "No available trainers found";
    public static final int MORNING_FIRST_SLOT_START_TIME = 6;
    public static final int MORNING_FIRST_SLOT_END_TIME = 8;
    public static final int MORNING_SECOND_SLOT_START_TIME = 8;
    public static final int MORNING_SECOND_SLOT_END_TIME = 10;
    public static final int EVENING_FIRST_SLOT_START_TIME = 16;
    public static final int EVENING_FIRST_SLOT_END_TIME = 19;
    public static final int EVENING_SECOND_SLOT_START_TIME = 19;
    public static final int EVENING_SECOND_SLOT_END_TIME = 22;
    public static final int PERSON_PER_SLOT = 2;
    public static final int[] SLOT_TIME = {MORNING_FIRST_SLOT_START_TIME,MORNING_FIRST_SLOT_END_TIME, MORNING_SECOND_SLOT_START_TIME,MORNING_SECOND_SLOT_END_TIME,
    EVENING_FIRST_SLOT_START_TIME,EVENING_FIRST_SLOT_END_TIME,EVENING_SECOND_SLOT_START_TIME,EVENING_SECOND_SLOT_END_TIME};
}


