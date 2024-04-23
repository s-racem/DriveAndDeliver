package com.racem.driveanddeliver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlot extends BaseEntity {
    LocalDateTime startTime;
    LocalDateTime endTime;
    boolean isBooked;

    @ManyToOne
    @JoinColumn(name = "delivery_option_id")
    DeliveryOption deliveryOption;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public TimeSlot(Long id, LocalDateTime startTime, LocalDateTime endTime, boolean isBooked, Customer customer) {
        setId(id);
        this.startTime = startTime;
        this.endTime = endTime;
        this.isBooked = isBooked;
        this.customer = customer;
    }
}
