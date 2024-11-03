package com.queueontario.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.queueontario.backend.models.WaitlistDTO;
import com.queueontario.backend.service.WaitlistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/waitlists")
public class WaitlistController {

    @Autowired
    private WaitlistService waitlistService;

    @GetMapping("/user/{id}")
    public ResponseEntity<List<WaitlistDTO>> getUserWaitlist(@PathVariable String id) {
        List<WaitlistDTO> waitlistDTOs = waitlistService.getWaitlists(id);

        return ResponseEntity.ok(waitlistDTOs);
    }    
}
