package com.racem.driveanddeliver.controller;

import com.racem.driveanddeliver.dto.DeliveryOptionDTO;
import com.racem.driveanddeliver.dto.TimeSlotDto;
import com.racem.driveanddeliver.service.base.BookingService;
import com.racem.driveanddeliver.service.impl.BookingServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Tag(name = "Booking Controller", description = "Booking Controller APIs")
public class BookingController {
    private final BookingServiceImpl bookingService;

    @PostMapping("/chooseOption")
    @Operation(
            summary = "Choose a delivery option",
            description = "Allow a customer to choose a delivery method by providing customer ID and delivery option.")
    public DeliveryOptionDTO chooseDeliveryOption(@RequestParam Long customerId,
                                                  @RequestParam String deliveryOption) {
        return bookingService.chooseDeliveryOption(customerId, deliveryOption);
    }

    @PostMapping("/bookTimeSlot")
    @Operation(
            summary = "Book a time slot",
            description = "Book a time slot for a customer by providing customer ID and time slot ID.")
    public TimeSlotDto bookTimeSlot(@RequestParam Long customerId,
                                    @RequestParam Long timeSlotId) {
        return bookingService.bookTimeSlot(customerId, timeSlotId);
    }
}
