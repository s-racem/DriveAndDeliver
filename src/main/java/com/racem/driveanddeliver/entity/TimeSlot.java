package com.racem.driveanddeliver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class TimeSlot extends BaseEntity {
    LocalDateTime startTime;
    LocalDateTime endTime;
    boolean isBooked;

    @ManyToOne
    @JoinColumn(name = "delivery_option_id") DeliveryOption deliveryOption;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
