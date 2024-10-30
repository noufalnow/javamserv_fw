package com.cboard.rental.tenants.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.cboard.rental.tenants.dto.BuildingDTO;
import com.cboard.rental.tenants.entity.Building;

@Mapper
public interface BuildingMapper {

    BuildingMapper INSTANCE = Mappers.getMapper(BuildingMapper.class);

    BuildingDTO buildingToBuildingDTO(Building building);

    Building buildingDTOToBuilding(BuildingDTO buildingDTO);
}
