package com.queueontario.backend.models;

public class WaitlistDTO {
    
    private String waitlistId;
    private String location;
    private int waitlistersAhead;
    private int estimatedWaitTime;      //in minutes


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

    public WaitlistDTO(String waitlistId, String location, int waitlistersAhead, int estimatedWaitTime)
    {
        this.waitlistId = waitlistId;
        this.location = location;
        this.waitlistersAhead = waitlistersAhead;
        this.estimatedWaitTime = estimatedWaitTime;
    }

    
}
