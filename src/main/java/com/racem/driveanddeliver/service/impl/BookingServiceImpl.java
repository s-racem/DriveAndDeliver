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
import com.racem.driveanddeliver.service.base.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final CustomerRepository customerRepository;
    private final DeliveryOptionRepository deliveryOptionRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final TimeSlotMapper timeSlotMapper;
    private final DeliveryOptionMapper deliveryOptionMapper;
    @Override
    public DeliveryOptionDTO chooseDeliveryOption(Long customerId, String method) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with ID " + customerId + " not found"));

        DeliveryOption deliveryOption = deliveryOptionRepository.findByMethod(method)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery option with method " + method + " not found"));

        customer.setDeliveryOption(deliveryOption);
        customerRepository.save(customer);

        return deliveryOptionMapper.deliveryOptionToDeliveryOptionDTO(deliveryOption);
    }

    @Override
    public TimeSlotDto bookTimeSlot(Long customerId, Long timeSlotId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with ID " + customerId + " not found"));

        TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId)
                .orElseThrow(() -> new ResourceNotFoundException("Time Slot with ID " + timeSlotId + " not found"));

        if (timeSlot.isBooked()) {
            throw new TimeSlotBookedException("Time slot is already booked");
        }

        timeSlot.setBooked(true);
        timeSlot.setCustomer(customer);

        TimeSlot updatedTimeSlot = timeSlotRepository.save(timeSlot);
        return timeSlotMapper.timeSlotToTimeSlotDTO(updatedTimeSlot);

    }
}
