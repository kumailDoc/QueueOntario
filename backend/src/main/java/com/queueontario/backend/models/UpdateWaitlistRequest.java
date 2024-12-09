package com.queueontario.backend.models;

import java.util.List;

/**
 * Represents a update waitlist request in the system.
 * This class contains the waitlist Id, average wait time,
 * and a list of users who registered for the waitlist.
 * It provides getters and setters for these fields.
 */
public class UpdateWaitlistRequest {
    private String waitlistId;
    private String averageWaitTime;
    private List<String> removeUserIds; // List of users to remove

    /**
     * gets the waitlist id
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

    /**
     * gets the list of user Ids
     * 
     * @return removeUserIds
     */
    public List<String> getRemoveUserIds() {
        return removeUserIds;
    }

    /**
     * sets the list of user Ids
     * 
     * @param removeUserIds
     */
    public void setRemoveUserIds(List<String> removeUserIds) {
        this.removeUserIds = removeUserIds;
    }
}
