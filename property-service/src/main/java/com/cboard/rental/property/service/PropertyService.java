package com.cboard.rental.property.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cboard.rental.property.config.ResourceNotFoundException;
import com.cboard.rental.property.dto.PropertyDTO;
import com.cboard.rental.property.entity.Property;
import com.cboard.rental.property.mapper.PropertyMapper;
import com.cboard.rental.property.repository.BuildingRepository;
import com.cboard.rental.property.repository.PropertyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    private final PropertyMapper mapper = PropertyMapper.INSTANCE;

    public PropertyDTO createProperty(PropertyDTO propertyDTO) {
        if (propertyDTO.getBuildingId() == null) {
            throw new IllegalArgumentException("Building ID must not be null");
        }
        Property property = mapper.propertyDTOToProperty(propertyDTO);

        // Validate and set the building
        property.setBuilding(buildingRepository.findById(propertyDTO.getBuildingId())
                .orElseThrow(() -> new ResourceNotFoundException("Building", propertyDTO.getBuildingId())));

        Property savedProperty = propertyRepository.save(property);
        return mapper.propertyToPropertyDTO(savedProperty);
    }

    public List<PropertyDTO> getAllProperties() {
        return propertyRepository.findAll().stream()
                .map(mapper::propertyToPropertyDTO)
                .collect(Collectors.toList());
    }

    public PropertyDTO getPropertyById(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property", id));
        return mapper.propertyToPropertyDTO(property);
    }

    public PropertyDTO updateProperty(Long id, PropertyDTO propertyDTO) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property", id));

        // Update property fields
        property.setPropertyName(propertyDTO.getPropertyName());
        property.setAddress(propertyDTO.getAddress());

        // Validate and update the building reference if provided
        if (propertyDTO.getBuildingId() != null) {
            property.setBuilding(buildingRepository.findById(propertyDTO.getBuildingId())
                    .orElseThrow(() -> new ResourceNotFoundException("Building", propertyDTO.getBuildingId())));
        }

        Property updatedProperty = propertyRepository.save(property);
        return mapper.propertyToPropertyDTO(updatedProperty);
    }

    public void deleteProperty(Long id) {
        if (!propertyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Property", id);
        }
        propertyRepository.deleteById(id);
    }
}
