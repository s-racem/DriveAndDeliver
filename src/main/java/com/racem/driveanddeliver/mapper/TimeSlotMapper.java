package com.racem.driveanddeliver.mapper;

import com.racem.driveanddeliver.dto.TimeSlotDto;
import com.racem.driveanddeliver.entity.TimeSlot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface TimeSlotMapper {
    TimeSlotMapper INSTANCE = Mappers.getMapper(TimeSlotMapper.class);

    @Mapping(source = "customer.id", target = "customerId")
    TimeSlotDto timeSlotToTimeSlotDTO(TimeSlot timeSlot);

    @Mapping(source = "customerId", target = "customer.id")
    TimeSlot timeSlotDTOToTimeSlot(TimeSlotDto timeSlotDTO);
}
