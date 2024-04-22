package com.racem.driveanddeliver.mapper;

import com.racem.driveanddeliver.dto.DeliveryOptionDTO;
import com.racem.driveanddeliver.entity.DeliveryOption;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DeliveryOptionMapper {
    DeliveryOptionMapper INSTANCE = Mappers.getMapper(DeliveryOptionMapper.class);

    DeliveryOptionDTO deliveryOptionToDeliveryOptionDTO(DeliveryOption deliveryOption);
    DeliveryOption deliveryOptionDTOToDeliveryOption(DeliveryOptionDTO deliveryOptionDTO);
}
