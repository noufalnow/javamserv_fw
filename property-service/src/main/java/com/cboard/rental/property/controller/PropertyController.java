package com.cboard.rental.property.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cboard.rental.property.config.ResourceNotFoundException;
import com.cboard.rental.property.dto.PropertyDTO;
import com.cboard.rental.property.service.PropertyService;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/properties")
public class PropertyController {
    
    private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    @Autowired
    private PropertyService propertyService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD')")
    public ResponseEntity<PropertyDTO> createProperty(@RequestBody PropertyDTO propertyDTO) {
        logger.info("Received PropertyDTO for creation: {}", propertyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.createProperty(propertyDTO));
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD')")
    public ResponseEntity<List<PropertyDTO>> getAllProperties() {
        return ResponseEntity.ok(propertyService.getAllProperties());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD')")
    public ResponseEntity<PropertyDTO> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }
    
    @GetMapping("/{propertyId}/exists")
    public ResponseEntity<Boolean> doesPropertyExist(@PathVariable Long propertyId) {
        // Logic to check if the property exists
        try {
            PropertyDTO property = propertyService.getPropertyById(propertyId);
            return ResponseEntity.ok(property != null); // Return true if property exists
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.ok(false); // Return false if property is not found
        }
    }

    

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD')")
    public ResponseEntity<PropertyDTO> updateProperty(@PathVariable Long id, @RequestBody PropertyDTO propertyDTO) {
    	
    	logger.info("Received PropertyDTO for creation: {}", propertyDTO);
        return ResponseEntity.ok(propertyService.updateProperty(id, propertyDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD')")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }
}
