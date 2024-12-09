package com.queueontario.backend.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a Waitlist in the system.
 * This class contains waitlist details such as
 * location, list of waitlisted users, if the list is active,
 * and the average wait time.
 * It provides getters and setters for these fields as well as
 * utility methods to interact with.
 */
@Document(collection = "waitlists")
public class Waitlist {

    @Id
    private String waitlistId;

    private String locationId;

    private List<String> waitlisters;

    private String isActive;

    private String averageWaitTime;

    /**
     * default constructor
     */
    public Waitlist() {
    }

    /**
     * Constructs a new waitlist with the specified details
     * 
     * @param waitlistId
     * @param locationId
     * @param waitlisters
     * @param isActive
     * @param averageWaitTime
     */
    public Waitlist(String waitlistId, String locationId, List<String> waitlisters, String isActive,
            String averageWaitTime) {
        this.waitlistId = waitlistId;
        this.locationId = locationId;
        this.waitlisters = waitlisters;
        this.isActive = isActive;
        this.averageWaitTime = averageWaitTime;
    }

    /**
     * gets the waitlist Id
     * 
     * @return waitlistId
     */
    public String getWaitlistId() {
        return waitlistId;
    }

    /**
     * sets the waitlistId
     * 
     * @param waitlistId
     */
    public void setWaitlistId(String waitlistId) {
        this.waitlistId = waitlistId;
    }

    /**
     * gets the location Id
     * 
     * @return locationId
     */
    public String getLocationId() {
        return locationId;
    }

    /**
     * sets the location id
     * 
     * @param locationId
     */
    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    /**
     * gets the list of waitlisters
     * 
     * @return waitlisters
     */
    public List<String> getWaitlisters() {
        return waitlisters;
    }

    /**
     * sets the waitlisters
     * 
     * @param waitlisters
     */
    public void setWaitlisters(List<String> waitlisters) {
        this.waitlisters = waitlisters;
    }

    /**
     * gets the isActive status
     * 
     * @return isActive
     */
    public String getIsActive() {
        return isActive;
    }

    /**
     * sets the isActive status
     * 
     * @param isActive
     */
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    /**
     * gets the average wait time
     * 
     * @return averageWaitTime
     */
    public String getAverageWaitTime() {
        return averageWaitTime;
    }

    /**
     * sets the average wait time
     * 
     * @param averageWaitTime
     */
    public void setAverageWaitTime(String averageWaitTime) {
        this.averageWaitTime = averageWaitTime;
    }

}
