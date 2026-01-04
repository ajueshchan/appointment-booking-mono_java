package com.arjun.appointment.service.Iservice;

import com.arjun.appointment.dto.request.BookingRequestDto;
import com.arjun.appointment.dto.response.BookingResponseDto;

public interface BookingService {
    BookingResponseDto persistToBooking(BookingRequestDto bookingRequestDto);
}
