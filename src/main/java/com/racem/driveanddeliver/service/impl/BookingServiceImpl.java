package com.racem.driveanddeliver.service.impl;

import com.racem.driveanddeliver.dto.DeliveryOptionDTO;
import com.racem.driveanddeliver.dto.TimeSlotDto;
import com.racem.driveanddeliver.service.base.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {
    @Override
    public DeliveryOptionDTO chooseDeliveryOption(Long customerId, String deliveryOption) {
        return null;
    }

    @Override
    public TimeSlotDto bookTimeSlot(Long customerId, Long timeSlotId) {
        return null;
    }
}
