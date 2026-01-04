package com.arjun.appointment.service;

import com.arjun.appointment.dto.request.BookingRequestDto;
import com.arjun.appointment.dto.response.BookingResponseDto;
import com.arjun.appointment.entity.Booking;
import com.arjun.appointment.entity.BookingStatus;
import com.arjun.appointment.entity.Slot;
import com.arjun.appointment.repository.BookingRepository;
import com.arjun.appointment.repository.SlotRepository;
import com.arjun.appointment.service.Iservice.BookingService;
import com.arjun.appointment.utils.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import static com.arjun.appointment.constant.AppointmentConstant.FALSE_AVAILABLE;
import static com.arjun.appointment.constant.AppointmentConstant.TRUE_AVAILABLE;

@Service
public class BookingServiceImpl implements BookingService {

    private final SlotRepository slotRepository;
    private final BookingRepository bookingRepository;

    public BookingServiceImpl(SlotRepository slotRepository,
                              BookingRepository bookingRepository){
      this.slotRepository = slotRepository;
      this.bookingRepository = bookingRepository;
    }

    @Override
    public BookingResponseDto persistToBooking(BookingRequestDto bookingRequestDto) {
        var booking = new Booking();
        var bookingResponse = new BookingResponseDto();
        Slot slot = slotRepository.findBySlotIdAndIsAvailable(bookingRequestDto.getSlotId(), TRUE_AVAILABLE)
                .orElse(null);
        if(null == slot){
            BeanUtils.copyProperties(bookingRequestDto,bookingResponse);
            bookingResponse.setBookingStatus(BookingStatus.FAILED.name());
            return bookingResponse;
        }
        BeanUtils.copyProperties(bookingRequestDto,booking);
        Booking persistedBooking = bookingRepository.save(booking);
        updateSlotAvailabilityForTrainers(slot);
        constructBookingResponse(bookingResponse, slot, persistedBooking);
        return bookingResponse;
    }

    private void updateSlotAvailabilityForTrainers(Slot slot) {
        slot.setIsAvailable(FALSE_AVAILABLE);
        slotRepository.save(slot);
    }

    private static void constructBookingResponse(BookingResponseDto bookingResponse, Slot slot, Booking persistedBooking) {
        bookingResponse.setBookingDate(slot.getSlotDate());
        bookingResponse.setSlotStartTime(DateUtil.formatHourAndMinuteTime(slot.getSlotStartTime()));
        bookingResponse.setSlotEndTime(DateUtil.formatHourAndMinuteTime(slot.getSlotEndTime()));
        bookingResponse.setBookingStatus(persistedBooking.getStatus());
        bookingResponse.setBookingId(persistedBooking.getBookingId());
        bookingResponse.setUserId(persistedBooking.getUserId());
        bookingResponse.setTrainerId(persistedBooking.getTrainerId());
    }
}
