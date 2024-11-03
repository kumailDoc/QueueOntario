package com.queueontario.backend.controllers;

import com.queueontario.backend.models.ServiceOntarioCenter;
import com.queueontario.backend.repository.ServiceOntarioCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/serviceontario")
public class ServiceOntarioCenterController {

    @Autowired
    private ServiceOntarioCenterRepository serviceOntarioCenterRepository;

    @GetMapping("/centers")
    public List<ServiceOntarioCenter> getCentersByCity(@RequestParam String city) {
        return serviceOntarioCenterRepository.findByCity(city);
    }
}
