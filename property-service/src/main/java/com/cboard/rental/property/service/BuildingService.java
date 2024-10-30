package com.cboard.rental.property.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cboard.rental.property.dto.BuildingDTO;
import com.cboard.rental.property.entity.Building;
import com.cboard.rental.property.mapper.BuildingMapper;
import com.cboard.rental.property.repository.BuildingRepository;
import com.cboard.rental.property.config.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    private final BuildingMapper mapper = BuildingMapper.INSTANCE;

    public BuildingDTO createBuilding(BuildingDTO buildingDTO) {
        Building building = mapper.buildingDTOToBuilding(buildingDTO);
        Building savedBuilding = buildingRepository.save(building);
        return mapper.buildingToBuildingDTO(savedBuilding);
    }

    public List<BuildingDTO> getAllBuildings() {
        return buildingRepository.findAll().stream()
                .map(mapper::buildingToBuildingDTO)
                .collect(Collectors.toList());
    }

    public BuildingDTO getBuildingById(Long id) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Building", id));
        return mapper.buildingToBuildingDTO(building);
    }

    public BuildingDTO updateBuilding(Long id, BuildingDTO buildingDTO) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Building", id));
        building.setBuildingName(buildingDTO.getBuildingName());
        Building updatedBuilding = buildingRepository.save(building);
        return mapper.buildingToBuildingDTO(updatedBuilding);
    }

    public void deleteBuilding(Long id) {
        buildingRepository.deleteById(id);
    }
}
