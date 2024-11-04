package com.queueontario.backend.controllers;

import com.queueontario.backend.models.ServiceOntarioCenter;
import com.queueontario.backend.repository.ServiceOntarioCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/serviceontario")
public class ServiceOntarioCenterController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceOntarioCenterController.class);

    @Autowired
    private ServiceOntarioCenterRepository serviceOntarioCenterRepository;

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

}
