package com.racem.driveanddeliver.controller;

import com.racem.driveanddeliver.dto.DeliveryOptionDTO;
import com.racem.driveanddeliver.dto.TimeSlotDto;
import com.racem.driveanddeliver.service.impl.BookingServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Tag(name = "Booking Controller", description = "Booking Controller APIs")
public class BookingController {
    private final BookingServiceImpl bookingService;

    @PostMapping("/chooseOption")
    @Operation(summary = "Choose a delivery option",
            description = "Allow a customer to choose a delivery method by providing customer ID and delivery option.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Delivery option successfully assigned to customer"),
                    @ApiResponse(responseCode = "404", description = "Customer or delivery option not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid parameters")
            })
    public ResponseEntity<DeliveryOptionDTO> chooseDeliveryOption(@RequestParam @NotNull(message = "customerId is mandatory") Long customerId,
                                                  @RequestParam @NotBlank(message = "deliveryOption should not be blank") String deliveryOption) {
        DeliveryOptionDTO deliveryOptionDTO = bookingService.chooseDeliveryOption(customerId, deliveryOption);
        return ResponseEntity.status(HttpStatus.CREATED).body(deliveryOptionDTO);
    }

    @PostMapping("/bookTimeSlot")
    @Operation(summary = "Book a time slot",
            description = "Book a time slot for a customer by providing customer ID and time slot ID.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Time slot booked successfully"),
                    @ApiResponse(responseCode = "404", description = "Customer or time slot not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid parameters")
            })
    public ResponseEntity<TimeSlotDto> bookTimeSlot(@RequestParam @NotNull(message = "customerId is mandatory") Long customerId,
                                    @RequestParam @NotNull(message = "timeSlotId is mandatory") Long timeSlotId) {
        TimeSlotDto timeSlot = bookingService.bookTimeSlot(customerId, timeSlotId);
        return ResponseEntity.status(HttpStatus.CREATED).body(timeSlot);
    }
}
