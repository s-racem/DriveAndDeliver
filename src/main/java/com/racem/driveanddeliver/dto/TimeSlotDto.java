package com.racem.driveanddeliver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlotDto {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isBooked;
    private Long customerId;

    public TimeSlotDto(Long id, LocalDateTime startTime, LocalDateTime endTime, boolean booked) {
        this.setId(id);
        this.startTime = startTime;
        this.endTime = endTime;
        this.setBooked(booked);
    }
}
