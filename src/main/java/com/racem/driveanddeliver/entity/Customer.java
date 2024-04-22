package com.racem.driveanddeliver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Customer extends BaseEntity {
    private String name;
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private Set<TimeSlot> bookedTimeSlots = new HashSet<>();
}