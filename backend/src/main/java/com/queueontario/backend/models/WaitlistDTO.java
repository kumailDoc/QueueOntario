package com.queueontario.backend.models;

public class WaitlistDTO {
    
    private String waitlistId;
    private String location;
    private int waitlistersAhead;
    private int estimatedWaitTime;      //in minutes

    private String locationName;
    private String locationAddress;
    private String locationCity;
    private String isActive;


    public String getWaitlistId() {
        return waitlistId;
    }

    public void setWaitlistId(String waitlistId) {
        this.waitlistId = waitlistId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getWaitlistersAhead() {
        return waitlistersAhead;
    }

    public void setWaitlistersAhead(int waitlistersAhead) {
        this.waitlistersAhead = waitlistersAhead;
    }

    public int getEstimatedWaitTime() {
        return estimatedWaitTime;
    }

    public void setEstimatedWaitTime(int estimatedWaitTime) {
        this.estimatedWaitTime = estimatedWaitTime;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }


    public WaitlistDTO(String waitlistId, String location, int waitlistersAhead, int estimatedWaitTime)
    {
        this.waitlistId = waitlistId;
        this.location = location;
        this.waitlistersAhead = waitlistersAhead;
        this.estimatedWaitTime = estimatedWaitTime;
    }

    // Overloaded Constructor
    public WaitlistDTO(String waitlistId, String location, int waitlistersAhead, int estimatedWaitTime, 
                   String locationName, String locationAddress, String locationCity, String isActive) {
    this.waitlistId = waitlistId;
    this.location = location;
    this.waitlistersAhead = waitlistersAhead;
    this.estimatedWaitTime = estimatedWaitTime;
    this.locationName = locationName;
    this.locationAddress = locationAddress;
    this.locationCity = locationCity;
    this.isActive = isActive;
}
public WaitlistDTO() {
    // Default constructor
}



    
}
