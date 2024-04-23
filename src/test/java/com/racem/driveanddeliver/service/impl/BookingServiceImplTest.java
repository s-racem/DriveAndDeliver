package com.racem.driveanddeliver.service.impl;

import com.racem.driveanddeliver.dto.DeliveryOptionDTO;
import com.racem.driveanddeliver.dto.TimeSlotDto;
import com.racem.driveanddeliver.entity.Customer;
import com.racem.driveanddeliver.entity.DeliveryOption;
import com.racem.driveanddeliver.entity.TimeSlot;
import com.racem.driveanddeliver.mapper.DeliveryOptionMapper;
import com.racem.driveanddeliver.mapper.TimeSlotMapper;
import com.racem.driveanddeliver.repository.CustomerRepository;
import com.racem.driveanddeliver.repository.DeliveryOptionRepository;
import com.racem.driveanddeliver.repository.TimeSlotRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {
    @Mock
    private DeliveryOptionRepository deliveryOptionRepository;
    @Mock
    private TimeSlotRepository timeSlotRepository;
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private DeliveryOptionMapper deliveryOptionMapper;

    @Mock
    private TimeSlotMapper timeSlotMapper;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    public void testBookTimeSlot() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1);
        TimeSlot slot = new TimeSlot(1L, startTime, endTime, false, null);
        Customer customer = new Customer(1L, "John Doe");

        when(timeSlotRepository.findById(1L)).thenReturn(Optional.of(slot));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        slot.setCustomer(customer);  // Link the customer to the time slot
        when(timeSlotMapper.timeSlotToTimeSlotDTO(slot)).thenReturn(new TimeSlotDto(1L, startTime, endTime, true, 1L));

        TimeSlotDto result = bookingService.bookTimeSlot(1L, 1L);

        assertTrue(result.isBooked());
        assertEquals(1L, result.getCustomerId());
        verify(timeSlotRepository).save(slot);  // Ensure it's saved with 'booked' status and linked customer
    }

    @Test
    public void testBookTimeSlotAlreadyBooked() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1);
        TimeSlot slot = new TimeSlot(1L, startTime, endTime, true, null);

        when(timeSlotRepository.findById(1L)).thenReturn(Optional.of(slot));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookingService.bookTimeSlot(1L, 1L);
        });

        assertEquals("Time slot is already booked", exception.getMessage());
    }

    @Test
    public void testChooseDeliveryOptionSuccess() {
        // Arrange
        Long customerId = 1L;
        String method = "DRIVE";
        DeliveryOption deliveryOption = new DeliveryOption(1L, method);
        DeliveryOptionDTO expectedDto = new DeliveryOptionDTO(1L, method);

        when(deliveryOptionRepository.findByMethod(method)).thenReturn(Optional.of(deliveryOption));
        when(deliveryOptionMapper.deliveryOptionToDeliveryOptionDTO(deliveryOption)).thenReturn(expectedDto);

        // Act
        DeliveryOptionDTO result = bookingService.chooseDeliveryOption(customerId, method);

        // Assert
        assertEquals(expectedDto, result);
    }

    @Test
    public void testChooseDeliveryOptionFailure() {
        // Arrange
        Long customerId = 1L;
        String method = "UNKNOWN";

        when(deliveryOptionRepository.findByMethod(method)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bookingService.chooseDeliveryOption(customerId, method);
        });

        assertEquals("Delivery option not available", exception.getMessage());
    }
}