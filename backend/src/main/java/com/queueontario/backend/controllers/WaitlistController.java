package com.queueontario.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.queueontario.backend.models.Waitlist;
import com.queueontario.backend.models.WaitlistDTO;
import com.queueontario.backend.repository.ServiceOntarioCenterRepository;
import com.queueontario.backend.service.WaitlistService;
import com.queueontario.backend.service.WaitlistServiceImpl;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/waitlists")
public class WaitlistController {

    @Autowired
    private WaitlistService waitlistService;

    @Autowired
    private WaitlistServiceImpl waitlistServiceImpl;

    @Autowired 
    private ServiceOntarioCenterRepository serviceOntarioCenterRepository;

    @GetMapping("/user/{id}")
    public ResponseEntity<List<WaitlistDTO>> getUserWaitlist(@PathVariable String id) {
        List<WaitlistDTO> waitlistDTOs = waitlistService.getWaitlists(id);

        return ResponseEntity.ok(waitlistDTOs);
    }    

    //Join Waitlist
    @PostMapping("/add")
    public Waitlist addUserToWaitlist(@RequestBody AddUserRequest addUserRequest) {
        return waitlistServiceImpl.addUserToWaitlist(addUserRequest.getServiceOntarioCenterId(), addUserRequest.getUserId());
    }
    
    // Cancel Spot
    @DeleteMapping("/delete")
    public ResponseEntity<String> removeUserFromWaitlistByWaitlistId(@RequestBody RemoveUserRequest removeUserRequest) {
    System.out.println("Received request to remove user: " + removeUserRequest.getUserId() + " from waitlist: " + removeUserRequest.getWaitlistId() + " location: "+removeUserRequest.getServiceOntarioCenterId());

    boolean isRemoved = waitlistServiceImpl.removeUserFromWaitlistByWaitlistId(removeUserRequest.getWaitlistId(), removeUserRequest.getUserId());

    if (isRemoved) {
        return ResponseEntity.ok("User removed from waitlist.");
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or waitlist not found.");
    }

    }
    // Get all waitlists and the location details 
    @GetMapping("/getAll")
    public List<WaitlistDTO> getAllWaitlistsWithDetails() {
        return waitlistServiceImpl.getAllWaitlistsWithDetails();
    }
    


    // Update waitlist - Open/Close Waitlist Service
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/update-status")
    public ResponseEntity<String> updateWaitlistStatus(@RequestBody UpdateStatusRequest updateStatusRequest) {
        boolean isUpdated = waitlistServiceImpl.updateWaitlistStatus(updateStatusRequest.getWaitlistId(), updateStatusRequest.getIsActive());
        if (isUpdated) {
            return ResponseEntity.ok("Waitlist status updated.");
        } else {
            return ResponseEntity.status(404).body("Waitlist not found.");
        }
    }

    public static class AddUserRequest {
        private String serviceOntarioCenterId;
        private String userId;

        // Getters and setters
        public String getServiceOntarioCenterId() {
            return serviceOntarioCenterId;
        }

        public void setServiceOntarioCenterId(String serviceOntarioCenterId) {
            this.serviceOntarioCenterId = serviceOntarioCenterId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    public static class RemoveUserRequest {
        private String waitlistId;
        private String userId;
        private String serviceOntarioCenterId;
    
        // Getters and setters
        public String getWaitlistId() {
            return waitlistId;
        }
    
        public void setWaitlistId(String waitlistId) {
            this.waitlistId = waitlistId;
        }
        public String getServiceOntarioCenterId() {
            return serviceOntarioCenterId;
        }
    
        public void setServiceOntarioCenterId(String serviceOntarioCenterId) {
            this.serviceOntarioCenterId = serviceOntarioCenterId;
        }
    
        public String getUserId() {
            return userId;
        }
    
        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
    


    public static class UpdateStatusRequest {
        private String waitlistId;
        private String isActive;

        // Getters and setters
        public String getWaitlistId() {
            return waitlistId;
        }

        public void setWaitlistId(String waitlistId) {
            this.waitlistId = waitlistId;
        }

        public String getIsActive() {
            return isActive;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }
    }


}
