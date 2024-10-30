package com.cboard.rental.property.mapper;

import com.cboard.rental.property.dto.BuildingDTO;
import com.cboard.rental.property.entity.Building;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BuildingMapper {

    BuildingMapper INSTANCE = Mappers.getMapper(BuildingMapper.class);

    BuildingDTO buildingToBuildingDTO(Building building);

    Building buildingDTOToBuilding(BuildingDTO buildingDTO);
}
