package com.racem.driveanddeliver.controller;

import com.racem.driveanddeliver.dto.DeliveryOptionDTO;
import com.racem.driveanddeliver.dto.TimeSlotDto;
import com.racem.driveanddeliver.service.base.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private BookingService bookingService;

    @PostMapping("/chooseOption")
    public DeliveryOptionDTO chooseDeliveryOption(@RequestParam Long customerId, @RequestParam String deliveryOption) {
        return bookingService.chooseDeliveryOption(customerId, deliveryOption);
    }

    @PostMapping("/bookTimeSlot")
    public TimeSlotDto bookTimeSlot(@RequestParam Long customerId, @RequestParam Long timeSlotId) {
        return bookingService.bookTimeSlot(customerId, timeSlotId);
    }
}
