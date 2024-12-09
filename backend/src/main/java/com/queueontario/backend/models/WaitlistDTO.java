package com.queueontario.backend.models;

import java.util.List;
import java.util.Map;

/**
 * This is a data transfer object (DTO) representing
 * the details of a waitlist.
 * This class is used to transer waitlist and location data.
 */
public class WaitlistDTO {

    private String waitlistId;
    private String location;
    private int waitlistersAhead;
    private int estimatedWaitTime; // in minutes

    private String locationName;
    private String locationAddress;
    private String locationCity;
    private String isActive;
    private List<Map<String, String>> waitlisters;

    /**
     * gets the waitlist Id
     * 
     * @return waitlistId
     */
    public String getWaitlistId() {
        return waitlistId;
    }

    /**
     * sets the waitlist Id
     * 
     * @param waitlistId
     */
    public void setWaitlistId(String waitlistId) {
        this.waitlistId = waitlistId;
    }

    /**
     * gets the location
     * 
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * sets the location
     * 
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * gets the number of waitlisters ahead
     * 
     * @return waitlistersAhead
     */
    public int getWaitlistersAhead() {
        return waitlistersAhead;
    }

    /**
     * sets the number of waitlisters ahead
     * 
     * @param waitlistersAhead
     */
    public void setWaitlistersAhead(int waitlistersAhead) {
        this.waitlistersAhead = waitlistersAhead;
    }

    /**
     * gets the estimated wait time
     * 
     * @return estimatedWaitTime
     */
    public int getEstimatedWaitTime() {
        return estimatedWaitTime;
    }

    /**
     * sets the estimated wait time
     * 
     * @param estimatedWaitTime
     */
    public void setEstimatedWaitTime(int estimatedWaitTime) {
        this.estimatedWaitTime = estimatedWaitTime;
    }

    /**
     * get the location's name
     * 
     * @return locationName
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * sets the location's name
     * 
     * @param locationName
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    /**
     * get the location's address
     * 
     * @return locationAddress
     */
    public String getLocationAddress() {
        return locationAddress;
    }

    /**
     * sets the location's address
     * 
     * @param locationAddress
     */
    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    /**
     * get the location's city
     * 
     * @return locationCity
     */
    public String getLocationCity() {
        return locationCity;
    }

    /**
     * sets the location's city
     * 
     * @param locationCity
     */
    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    /**
     * get the is active status
     * 
     * @return isActive
     */
    public String getIsActive() {
        return isActive;
    }

    /**
     * sets the is active status
     * 
     * @param isActive
     */
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    /**
     * gets the list of waitlisters
     * 
     * @return waitlisters
     */
    public List<Map<String, String>> getWaitlisters() {
        return waitlisters;
    }

    /**
     * sets the list of waitlisters
     * 
     * @param waitlisters
     */
    public void setWaitlisters(List<Map<String, String>> waitlisters) {
        this.waitlisters = waitlisters;
    }

    /**
     * constructs a new waitlistDTO
     * 
     * @param waitlistId        the unique id
     * @param location          the location
     * @param waitlistersAhead  the number of waitlisted people ahead
     * @param estimatedWaitTime the estimated wait time
     */
    public WaitlistDTO(String waitlistId, String location, int waitlistersAhead, int estimatedWaitTime) {
        this.waitlistId = waitlistId;
        this.location = location;
        this.waitlistersAhead = waitlistersAhead;
        this.estimatedWaitTime = estimatedWaitTime;
    }

    /**
     * constructs a new waitlistDTO
     * 
     * @param waitlistId        the unique Id
     * @param location          the waitlist's location
     * @param waitlistersAhead  the number of waitlisters ahead
     * @param estimatedWaitTime the estimated wait time
     * @param locationName      the waitlist location's name
     * @param locationAddress   the waitlist location's address
     * @param locationCity      the waitlist location's city
     * @param isActive          the waitlist's status
     */
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

    /**
     * default constructor
     */
    public WaitlistDTO() {

    }

}
