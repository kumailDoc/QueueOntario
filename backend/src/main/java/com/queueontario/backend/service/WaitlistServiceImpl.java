package com.queueontario.backend.service;
import com.queueontario.backend.models.ServiceOntarioCenter;
import com.queueontario.backend.models.UserWaitList;
import com.queueontario.backend.models.Waitlist;
import com.queueontario.backend.repository.ServiceOntarioCenterRepository;
import com.queueontario.backend.repository.UserWaitlistRepo;
import com.queueontario.backend.repository.WaitlistRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.client.result.UpdateResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


    public Waitlist addUserToWaitlist(String serviceOntarioCenterId, String userId) {
        Optional<Waitlist> optionalWaitlist = waitlistRepository.findByLocationId(serviceOntarioCenterId);
        Waitlist waitlist;
    
        if (optionalWaitlist.isPresent()) {
            waitlist = optionalWaitlist.get();
        } else {
            waitlist = new Waitlist();
            waitlist.setLocationId(serviceOntarioCenterId);
            waitlist.setWaitlisters(new ArrayList<>());
            waitlist.setIsActive("true");
            waitlist.setAverageWaitTime("20");
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
        System.out.println("WaitlistId: " + waitlistId);
        System.out.println("UserId: " + userId);
    
        Optional<Waitlist> optionalWaitlist = waitlistRepository.findById(waitlistId);
    
        if (optionalWaitlist.isPresent()) {
            System.out.println("Waitlist found");
            Waitlist waitlist = optionalWaitlist.get();
            System.out.println("Current waitlisters: " + waitlist.getWaitlisters());
    
            for (String id : waitlist.getWaitlisters()) {
                System.out.println("Comparing " + id + " with " + userId);
                if (id.equals(userId)) {
                    System.out.println("User found in waitlist");
                    waitlist.getWaitlisters().remove(id); // Remove user from waitlist
                    waitlistRepository.save(waitlist); // Save updated waitlist
                    System.out.println("User removed successfully");
                    return true; // Return true to indicate successful removal
                }
            }
            System.out.println("User not found in waitlist after comparison");
        } else {
            System.out.println("Waitlist not found");
        }
        return false; // Return false if waitlist not found or user not in waitlist
    }
    

    // Change Waitlist Status
    public boolean updateWaitlistStatus(String waitlistId, String isActive) {
        System.out.println("Updating status for waitlist ID: " + waitlistId + " to " + isActive);

        Optional<Waitlist> optionalWaitlist = waitlistRepository.findById(waitlistId);
        if (optionalWaitlist.isPresent()) {
            Waitlist waitlist = optionalWaitlist.get();
            waitlist.setIsActive(isActive);

            // Save and check the returned updated object
            Waitlist updatedWaitlist = waitlistRepository.save(waitlist);
            System.out.println("Updated waitlist status: " + updatedWaitlist.getIsActive());

            // Final verification for debugging
            return updatedWaitlist.getIsActive().equals(isActive);
        } else {
            System.out.println("Waitlist not found for ID: " + waitlistId);
            return false;
        }
    }

}
