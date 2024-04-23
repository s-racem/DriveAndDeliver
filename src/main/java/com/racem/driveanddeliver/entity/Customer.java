package com.racem.driveanddeliver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer extends BaseEntity {
    private String name;
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private Set<TimeSlot> bookedTimeSlots = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_option_id")
    private DeliveryOption deliveryOption;

    public Customer(long id, String name) {
        super();
        setId(id);
        this.name = name;
    }
}
