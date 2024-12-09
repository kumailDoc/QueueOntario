package com.queueontario.backend.controllers;

import com.queueontario.backend.models.ServiceOntarioCenter;
import com.queueontario.backend.models.UpdateLocationRequest;
import com.queueontario.backend.repository.ServiceOntarioCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Controller for managing service ontrio center related operations.
 * This controller exposes APIs for service ontario center
 * get list of all centers, get centers by city, and update center location.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/serviceontario")
public class ServiceOntarioCenterController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceOntarioCenterController.class);

    @Autowired
    private ServiceOntarioCenterRepository serviceOntarioCenterRepository;

    /**
     * gets a list of all service ontario centers with their details.
     * This method handles the HTTP GET requests to the
     * '/api/serviceontario/centers' endpoint.
     * 
     * @param city the city where the service ontario center is located at
     * @return a ResponseEntity containing the list of centers
     */
    @GetMapping("/centers")
    public ResponseEntity<List<ServiceOntarioCenter>> getCentersByCity(@RequestParam(name = "city") String city) {
        logger.info("Received request to fetch service centers for city: {}", city);

        List<ServiceOntarioCenter> centers = serviceOntarioCenterRepository.findByCity(city);
        if (centers.isEmpty()) {
            logger.warn("No service centers found for city: {}", city);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(centers);
        }

        logger.info("Found {} service centers for city: {}", centers.size(), city);
        return ResponseEntity.ok(centers);
    }

    /**
     * gets a list of all service ontario centers with their details.
     * This method handles the HTTP GET requests to the
     * '/api/serviceontario/all-centers' endpoint.
     * 
     * @return a ResponseEntity containing the list of centers
     */
    @GetMapping("/all-centers")
    public ResponseEntity<List<ServiceOntarioCenter>> getAllCenters() {
        logger.info("Received request to fetch all service centers.");
        List<ServiceOntarioCenter> centers = serviceOntarioCenterRepository.findAll();
        if (centers.isEmpty()) {
            logger.warn("No service centers found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(centers);
        }
        logger.info("Found {} service centers.", centers.size());
        return ResponseEntity.ok(centers);
    }

    /**
     * updates a service ontario center location's address.
     * This method handles HTTP PUT requests to the
     * '/api/serviceontario/admin/update-location' endpoint.
     *
     * @param request the request body containing the updated address
     * @return a ResponseEntity containing the result and status
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/update-location")
    public ResponseEntity<String> updateLocation(@RequestBody UpdateLocationRequest request) {
        try {
            // Retrieve the location by ID
            Optional<ServiceOntarioCenter> optionalCenter = serviceOntarioCenterRepository
                    .findById(request.getLocationId());

            if (optionalCenter.isPresent()) {
                // Update the address field
                ServiceOntarioCenter center = optionalCenter.get();
                center.setAddress(request.getAddress());

                // Save the updated center back to the database
                serviceOntarioCenterRepository.save(center);

                return ResponseEntity.ok("Location updated successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Location not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating location: " + e.getMessage());
        }
    }

}
