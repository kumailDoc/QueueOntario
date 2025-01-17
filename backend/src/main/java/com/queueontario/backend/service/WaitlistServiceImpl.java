package com.queueontario.backend.service;
import com.queueontario.backend.models.ServiceOntarioCenter;
import com.queueontario.backend.models.User;
import com.queueontario.backend.models.UserWaitList;
import com.queueontario.backend.models.Waitlist;
import com.queueontario.backend.models.WaitlistDTO;
import com.queueontario.backend.repository.ServiceOntarioCenterRepository;
import com.queueontario.backend.repository.UserRepository;
import com.queueontario.backend.repository.UserWaitlistRepo;
import com.queueontario.backend.repository.WaitlistRepo;
import com.queueontario.backend.utils.EmailService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mongodb.client.result.UpdateResult;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.queueontario.backend.models.User;

@Service
public class WaitlistServiceImpl {
    @Autowired
    private WaitlistRepo  waitlistRepository;
    @Autowired
    private ServiceOntarioCenterRepository serviceOntarioCenterRepository;

    @Autowired
    private UserWaitlistRepo userWaitListRepository;

    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private UserRepository userRepository;


    public Waitlist addUserToWaitlist(String serviceOntarioCenterId, String userId) {
        Optional<Waitlist> optionalWaitlist = waitlistRepository.findByLocationId(serviceOntarioCenterId);
        Waitlist waitlist;
    
        if (optionalWaitlist.isPresent()) {
            waitlist = optionalWaitlist.get();
            System.out.println("Retrieved waitlist with isActive status: " + waitlist.getIsActive());
        } else {
            waitlist = new Waitlist();
            waitlist.setLocationId(serviceOntarioCenterId);
            waitlist.setWaitlisters(new ArrayList<>());
            waitlist.setIsActive("true");
            waitlist.setAverageWaitTime("20");
        }
    
        // Check if the waitlist is active; return immediately if it’s inactive
        if (!"true".equals(waitlist.getIsActive())) {
            System.out.println("Cannot join waitlist; this waitlist is not active for locationId: " + serviceOntarioCenterId);
            return waitlist;
        }
    
        if (waitlist.getWaitlisters().contains(userId)) {
            System.out.println("User already exists in the waitlist for locationId: " + serviceOntarioCenterId);
            return waitlist;
        }
    
        waitlist.getWaitlisters().add(userId);
        System.out.println("Adding user to waitlist with locationId: " + waitlist.getLocationId());
        waitlistRepository.save(waitlist);
    
        // Update the users_waitlist collection
        Optional<UserWaitList> optionalUserWaitList = userWaitListRepository.findUserByUserId(userId);
        UserWaitList userWaitList;
    
        if (optionalUserWaitList.isPresent()) {
            userWaitList = optionalUserWaitList.get();
            System.out.println("Found existing UserWaitList for userId: " + userId);
        } else {
            userWaitList = new UserWaitList();
            userWaitList.setUserId(userId);
            userWaitList.setWaitlistIdList(new ArrayList<>());
            System.out.println("Creating new UserWaitList for userId: " + userId);
        }
    
        // Ensure initialization of waitlistIdList
        if (userWaitList.getWaitlistIdList() == null) {
            userWaitList.setWaitlistIdList(new ArrayList<>());
        }
    
        // Add waitlistId if not already present
        if (!userWaitList.getWaitlistIdList().contains(waitlist.getWaitlistId())) {
            userWaitList.getWaitlistIdList().add(waitlist.getWaitlistId());
            System.out.println("Adding waitlistId: " + waitlist.getWaitlistId() + " to userWaitList for userId: " + userId);
        } else {
            System.out.println("WaitlistId: " + waitlist.getWaitlistId() + " already exists for userWaitList and userId: " + userId);
        }
    
        userWaitListRepository.save(userWaitList);
        System.out.println("UserWaitList saved for userId: " + userId + " with waitlistId: " + waitlist.getWaitlistId());
        
     // Get user email and service center details to send email
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        ServiceOntarioCenter serviceOntarioCenter = serviceOntarioCenterRepository.findById(serviceOntarioCenterId)
                .orElseThrow(() -> new RuntimeException("Service Ontario Center not found"));

        String emailText = String.format("Hello %s,\n\nYou have successfully joined the waitlist for the Service Ontario center located at %s.\n\nThank you for using our service.\n\nQueueOntario Team",
                user.getUsername(), serviceOntarioCenter.getName());
        emailService.sendEmail(user.getEmail(), "Waitlist Confirmation", emailText);
    
        return waitlist;
    }
    
    private Waitlist createNewWaitlist(ServiceOntarioCenter center) {
        Waitlist waitlist = new Waitlist();
        waitlist.setWaitlistId(center.getWaitlistId());
        waitlist.setWaitlisters(new ArrayList<>());
        waitlist.setIsActive("true");
        waitlist.setAverageWaitTime("30");
        waitlistRepository.save(waitlist);
        return waitlist;
    }

    public boolean removeUserFromWaitlistByWaitlistId(String waitlistId, String userId) {
        System.out.println("Received request to remove user: " + userId + " from waitlist: " + waitlistId);
    
        Optional<Waitlist> optionalWaitlist = waitlistRepository.findById(waitlistId);
    
        if (optionalWaitlist.isPresent()) {
            System.out.println("Waitlist found with waitlistId: " + waitlistId);
            Waitlist waitlist = optionalWaitlist.get();
    
            System.out.println("Attempting to remove userId from waitlisters list.");
            boolean isRemoved = waitlist.getWaitlisters().remove(userId); // Remove user from waitlist
            if (isRemoved) {
                waitlistRepository.save(waitlist);
                System.out.println("User " + userId + " removed from waitlist " + waitlistId + " successfully.");
    
                // Attempting to delete from users_waitlist
                System.out.println("Attempting to delete UserWaitList record for userId: " + userId);
                try {
                    userWaitListRepository.deleteByUserId(userId);
                    System.out.println("Delete operation called for UserWaitList with userId: " + userId);
                    
                    // Verification
                    Optional<UserWaitList> checkDeletion = userWaitListRepository.findUserByUserId(userId);
                    if (checkDeletion.isPresent()) {
                        System.out.println("UserWaitList record still exists for userId: " + userId);
                    } else {
                        System.out.println("UserWaitList record successfully deleted for userId: " + userId);
                    }
                } catch (Exception e) {
                    System.out.println("Exception during delete operation: " + e.getMessage());
                }
                return true;
            } else {
                System.out.println("User " + userId + " not found in waitlist.");
            }
        } else {
            System.out.println("Waitlist not found for waitlistId: " + waitlistId);
        }
        return false;
    }
    

    // Change Waitlist Status
    public boolean updateWaitlistStatus(String waitlistId, String isActive) {
        System.out.println("Updating status for waitlist ID: " + waitlistId + " to " + isActive);
    
        // Fetch waitlist and update its status
        return waitlistRepository.findById(waitlistId).map(waitlist -> {
            waitlist.setIsActive(isActive);
            waitlistRepository.save(waitlist); // Save updated waitlist
            System.out.println("Waitlist status updated successfully to: " + isActive);
            return true;
        }).orElseGet(() -> {
            System.out.println("Waitlist not found for ID: " + waitlistId);
            return false;
        });
    }
    
   public List<WaitlistDTO> getAllWaitlistsWithDetails() {
    // Fetch all waitlists
    List<Waitlist> waitlists = waitlistRepository.findAll();

    // Pre-fetch all ServiceOntarioCenter data 
    Map<String, ServiceOntarioCenter> locationMap = serviceOntarioCenterRepository
        .findAll()
        .stream()
        .collect(Collectors.toMap(ServiceOntarioCenter::getId, center -> center)); 

    // Pre-fetch all user data
    Map<String, String> userMap = userRepository
        .findAll()
        .stream()
        .collect(Collectors.toMap(User::getId, User::getUsername));    

    // Convert waitlists to DTOs
    List<WaitlistDTO> waitlistDTOs = new ArrayList<>();
    for (Waitlist waitlist : waitlists) {
        WaitlistDTO waitlistDTO = new WaitlistDTO();
        waitlistDTO.setWaitlistId(waitlist.getWaitlistId());
        waitlistDTO.setIsActive(waitlist.getIsActive());
        waitlistDTO.setEstimatedWaitTime(Integer.parseInt(waitlist.getAverageWaitTime())); 
        waitlistDTO.setLocation(waitlist.getLocationId());

        // Get location details from pre-fetched map
        ServiceOntarioCenter center = locationMap.get(waitlist.getLocationId());
        if (center != null) {
            waitlistDTO.setLocationName(center.getName());
            waitlistDTO.setLocationAddress(center.getAddress());
            waitlistDTO.setLocationCity(center.getCity());
        }
        // Map user IDs to usernames
        // List<String> usernames = waitlist.getWaitlisters()
        //     .stream()
        //     .map(userMap::get)
        //     .collect(Collectors.toList());
        // waitlistDTO.setWaitlisters(usernames);
        List<Map<String, String>> detailedWaitlisters = waitlist.getWaitlisters()
            .stream()
            .map(userId -> {
                Map<String, String> userDetail = new HashMap<>();
                userDetail.put("userId", userId);
                userDetail.put("username", userMap.get(userId)); // Map userId to username
                return userDetail;
            })
            .collect(Collectors.toList());

        waitlistDTO.setWaitlisters(detailedWaitlisters);

        waitlistDTOs.add(waitlistDTO);
    }
    return waitlistDTOs;
}

    public boolean updateWaitlist(String waitlistId, String averageWaitTime, List<String> removeUserIds) {
        Optional<Waitlist> optionalWaitlist = waitlistRepository.findById(waitlistId);

        if (optionalWaitlist.isPresent()) {
            Waitlist waitlist = optionalWaitlist.get();

            // Update average wait time
            if (averageWaitTime != null) {
                waitlist.setAverageWaitTime(averageWaitTime);
            }

            // Remove users from the waitlist
            if (removeUserIds != null && !removeUserIds.isEmpty()) {
                System.out.println("Initial waitlisters: " + waitlist.getWaitlisters());
                System.out.println("Users to remove: " + removeUserIds);

                // Attempt to remove users from waitlist
                boolean removedFromWaitlist = waitlist.getWaitlisters().removeAll(removeUserIds);
                System.out.println("Users removed from waitlist: " + removedFromWaitlist);

                // Remove users from the `users_waitlist` collection
                for (String userId : removeUserIds) {
                    try {
                        userWaitListRepository.deleteByUserId(userId);
                        System.out.println("Successfully removed userId: " + userId + " from users_waitlist");
                    } catch (Exception e) {
                        System.out.println("Failed to remove userId: " + userId + " from users_waitlist. Error: " + e.getMessage());
                    }
                }
            }

            // Save the updated waitlist
            waitlistRepository.save(waitlist);
            System.out.println("Updated waitlist saved successfully.");
            return true;
        }

        System.out.println("Waitlist not found for ID: " + waitlistId);
        return false;
    }


}
