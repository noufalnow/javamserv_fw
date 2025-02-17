package com.cboard.rental.property.mapper;

import com.cboard.rental.property.dto.PropertyDTO;
import com.cboard.rental.property.entity.Property;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PropertyMapper {

    PropertyMapper INSTANCE = Mappers.getMapper(PropertyMapper.class);

    // Map building relationship to buildingId in DTO
    @Mapping(target = "buildingId", source = "building.id")
    PropertyDTO propertyToPropertyDTO(Property property);

    // Map buildingId in DTO back to Building entity in Property
    @Mapping(target = "building", ignore = true) // Avoid directly mapping to nested property
    Property propertyDTOToProperty(PropertyDTO propertyDTO);
}
