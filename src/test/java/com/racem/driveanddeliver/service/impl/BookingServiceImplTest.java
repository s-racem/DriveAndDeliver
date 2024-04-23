package com.racem.driveanddeliver.service.impl;

import com.racem.driveanddeliver.dto.DeliveryOptionDTO;
import com.racem.driveanddeliver.dto.TimeSlotDto;
import com.racem.driveanddeliver.entity.Customer;
import com.racem.driveanddeliver.entity.DeliveryOption;
import com.racem.driveanddeliver.entity.TimeSlot;
import com.racem.driveanddeliver.exception.ResourceNotFoundException;
import com.racem.driveanddeliver.exception.TimeSlotBookedException;
import com.racem.driveanddeliver.mapper.DeliveryOptionMapper;
import com.racem.driveanddeliver.mapper.TimeSlotMapper;
import com.racem.driveanddeliver.repository.CustomerRepository;
import com.racem.driveanddeliver.repository.DeliveryOptionRepository;
import com.racem.driveanddeliver.repository.TimeSlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    private Customer customer;
    private TimeSlot timeSlot;
    private TimeSlotDto timeSlotDto;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);

        timeSlot = new TimeSlot();
        timeSlot.setId(1L);
        timeSlot.setBooked(false);

        timeSlotDto = new TimeSlotDto();
        timeSlotDto.setId(1L);
    }

    @Test
    void bookTimeSlot_Success() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(timeSlotRepository.findById(1L)).thenReturn(Optional.of(timeSlot));
        when(timeSlotRepository.save(any(TimeSlot.class))).thenReturn(timeSlot);
        when(timeSlotMapper.timeSlotToTimeSlotDTO(any(TimeSlot.class))).thenReturn(timeSlotDto);

        TimeSlotDto result = bookingService.bookTimeSlot(1L, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(timeSlotRepository).save(timeSlot);
        assertTrue(timeSlot.isBooked());
        verify(timeSlotMapper).timeSlotToTimeSlotDTO(timeSlot);
    }

    @Test
    void bookTimeSlot_CustomerNotFound_ThrowsException() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            bookingService.bookTimeSlot(1L, 1L);
        });

        assertEquals("Customer with ID 1 not found", exception.getMessage());
    }

    @Test
    void bookTimeSlot_TimeSlotNotFound_ThrowsException() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(timeSlotRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            bookingService.bookTimeSlot(1L, 1L);
        });

        assertEquals("Time Slot with ID 1 not found", exception.getMessage());
    }

    @Test
    void bookTimeSlot_AlreadyBooked_ThrowsException() {
        timeSlot.setBooked(true);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(timeSlotRepository.findById(1L)).thenReturn(Optional.of(timeSlot));

        Exception exception = assertThrows(TimeSlotBookedException.class, () -> {
            bookingService.bookTimeSlot(1L, 1L);
        });

        assertEquals("Time slot is already booked", exception.getMessage());
    }

    @Test
    void chooseDeliveryOption_Success() {
        // Setup
        Long customerId = 1L;
        String method = "DELIVERY";
        Customer customer = new Customer();
        customer.setId(customerId);

        DeliveryOption deliveryOption = new DeliveryOption();
        deliveryOption.setMethod(method);

        DeliveryOptionDTO expectedDTO = new DeliveryOptionDTO();
        expectedDTO.setMethod(method);

        // Stubbing
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(deliveryOptionRepository.findByMethod(method)).thenReturn(Optional.of(deliveryOption));
        when(deliveryOptionMapper.deliveryOptionToDeliveryOptionDTO(deliveryOption)).thenReturn(expectedDTO);

        // Execution
        DeliveryOptionDTO result = bookingService.chooseDeliveryOption(customerId, method);

        // Verify
        assertNotNull(result);
        assertEquals(method, result.getMethod());
        verify(customerRepository).save(customer);
    }

    @Test
    void chooseDeliveryOption_CustomerNotFound_ThrowsException() {
        Long customerId = 1L;
        String method = "DELIVERY";

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            bookingService.chooseDeliveryOption(customerId, method);
        });
    }

    @Test
    void chooseDeliveryOption_DeliveryOptionNotFound_ThrowsException() {
        Long customerId = 1L;
        String method = "DELIVERY";
        Customer customer = new Customer();
        customer.setId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(deliveryOptionRepository.findByMethod(method)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            bookingService.chooseDeliveryOption(customerId, method);
        });
    }
}