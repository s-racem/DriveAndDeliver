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
        log.info("Choose delivery option  {} for customer {}", method, customerId);
        Customer customer = getCustomer(customerId);

        DeliveryOption deliveryOption = deliveryOptionRepository.findByMethod(method)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery option with method " + method + " not found"));

        customer.setDeliveryOption(deliveryOption);
        customerRepository.save(customer);

        log.info("Delivery option {} assigned successfully for customer {}", method, customerId);
        return deliveryOptionMapper.deliveryOptionToDeliveryOptionDTO(deliveryOption);
    }

    @Override
    public TimeSlotDto bookTimeSlot(Long customerId, Long timeSlotId) {
        log.info("Booking time slot {} for customer {}", timeSlotId, customerId);
        Customer customer = getCustomer(customerId);

        TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId)
                .orElseThrow(() -> new ResourceNotFoundException("Time Slot with ID " + timeSlotId + " not found"));

        if (timeSlot.isBooked()) {
            log.warn("Attempted to book an already booked time slot {}", timeSlotId);
            throw new TimeSlotBookedException("Time slot is already booked");
        }

        timeSlot.setBooked(true);
        timeSlot.setCustomer(customer);

        TimeSlot updatedTimeSlot = timeSlotRepository.save(timeSlot);
        log.info("Time slot {} booked successfully for customer {}", timeSlotId, customerId);

        return timeSlotMapper.timeSlotToTimeSlotDTO(updatedTimeSlot);

    }

    private Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with ID " + customerId + " not found"));
    }
}
