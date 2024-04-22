package com.racem.driveanddeliver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class DeliveryOption extends BaseEntity {
    private String method; // DRIVE, DELIVERY, DELIVERY_TODAY, DELIVERY_ASAP

    @OneToMany(mappedBy = "deliveryOption")
    private Set<TimeSlot> timeSlots = new HashSet<>();
}
