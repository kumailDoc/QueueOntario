package com.queueontario.backend.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="waitlists")
public class Waitlist {

    @Id
    private String waitlistId;

    private String locationId;

    private List<String> waitlisters;

    private String isActive;

    private String averageWaitTime;

    public Waitlist() {}

    public Waitlist(String waitlistId, String locationId, List<String> waitlisters, String isActive, String averageWaitTime)
    {
        this.waitlistId = waitlistId;
        this.locationId = locationId;
        this.waitlisters = waitlisters;
        this.isActive = isActive;
        this.averageWaitTime = averageWaitTime;
    }

    public String getWaitlistId() {
        return waitlistId;
    }

    public void setWaitlistId(String waitlistId) {
        this.waitlistId = waitlistId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public List<String> getWaitlisters() {
        return waitlisters;
    }

    public void setWaitlisters(List<String> waitlisters) {
        this.waitlisters = waitlisters;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getAverageWaitTime() {
        return averageWaitTime;
    }

    public void setAverageWaitTime(String averageWaitTime) {
        this.averageWaitTime = averageWaitTime;
    }

}
