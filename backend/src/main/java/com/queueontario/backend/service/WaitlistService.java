package com.queueontario.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.queueontario.backend.models.ServiceOntarioCenter;
import com.queueontario.backend.models.UserWaitList;
import com.queueontario.backend.models.Waitlist;
import com.queueontario.backend.models.WaitlistDTO;
import com.queueontario.backend.repository.ServiceOntarioCenterRepository;
import com.queueontario.backend.repository.UserWaitlistRepo;
import com.queueontario.backend.repository.WaitlistRepo;

@Service
public class WaitlistService {

    @Autowired
    private WaitlistRepo waitlistRepo;

    @Autowired
    private UserWaitlistRepo userWaitlistRepo;


    @Autowired 
    private ServiceOntarioCenterRepository serviceOntarioCenterRepo;

    public List<WaitlistDTO> getWaitlists(String userId) {
        System.out.println("searching for user waitlist with id " + userId);
        UserWaitList userWaitlist = userWaitlistRepo.findByUserId(userId);
        
        if (userWaitlist == null)
        {
            System.out.println("error: null userWaitlist");
            return null;
        }
        if(userWaitlist.getWaitlistIdList() == null)
        {
            System.out.println("error: can't get waitlist id list");
            return null;
        }

        List<WaitlistDTO> waitlistDTOList = new ArrayList<>();

        for (String id : userWaitlist.getWaitlistIdList()) {

            Waitlist w = waitlistRepo.findWaitlistByWaitlistId(id);
            waitlistDTOList.add(mapToWaitlistDTO(w, userId));
            System.out.println("waitlist found: " + w);
        }
        return waitlistDTOList;
    }

    public WaitlistDTO mapToWaitlistDTO(Waitlist waitlist, String userId) {

        int waitlistersAhead = waitlist.getWaitlisters().indexOf(userId);

        int avgWaitTime = Integer.parseInt(waitlist.getAverageWaitTime());
        int estimatedWaitTime = (waitlistersAhead >= 0) ? (avgWaitTime * waitlistersAhead) : 0;

        try{
            ServiceOntarioCenter serviceOntarioCenter = serviceOntarioCenterRepo.findById(waitlist.getLocationId()).orElseThrow(() -> new NoSuchElementException("Service Ontario Center entity not found"));
            String locationName = (serviceOntarioCenter != null) ? serviceOntarioCenter.getName() : "Unknown Location";
            WaitlistDTO newDTO = new WaitlistDTO(waitlist.getWaitlistId(), locationName, waitlistersAhead,
                estimatedWaitTime);

            return newDTO;
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e);
        }
    
        return null;
    }
}
