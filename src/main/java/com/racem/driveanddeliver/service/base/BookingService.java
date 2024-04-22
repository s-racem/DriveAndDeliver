package com.racem.driveanddeliver.service.base;

import com.racem.driveanddeliver.dto.DeliveryOptionDTO;
import com.racem.driveanddeliver.dto.TimeSlotDto;

public interface BookingService {
    DeliveryOptionDTO chooseDeliveryOption(Long customerId, String deliveryOption);
    TimeSlotDto
    bookTimeSlot(Long customerId, Long timeSlotId);
}
