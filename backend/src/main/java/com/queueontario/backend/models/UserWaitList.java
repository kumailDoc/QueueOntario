package com.queueontario.backend.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a user's entire waitlist list in the system.
 * This class contains the user's waitlist details such as
 * the userWaitlist id, the user's id, and the list of waitlists they are part
 * of.
 * It provides getters and setters for these fields.
 */
@Document(collection = "users_waitlist")
public class UserWaitList {

    @Id
    private String userWaitlistId;

    private String userId;

    private List<String> waitlistIdList;

    /**
     * gets the userWaitlistId
     * 
     * @return userWaitlistId
     */
    public String getUserWaitlistId() {
        return userWaitlistId;
    }

    /**
     * sets the userWaitlistId
     * 
     * @param userWaitlistId
     */
    public void setUserWaitlistId(String userWaitlistId) {
        this.userWaitlistId = userWaitlistId;
    }

    /**
     * gets the user's Id
     * 
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * sets the user's Id
     * 
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * gets the list of waitlists the user is in
     * 
     * @return waitlistIdList
     */
    public List<String> getWaitlistIdList() {
        return waitlistIdList;
    }

    /**
     * sets the list of waitlist the user is in
     * 
     * @param waitlistIdList
     */
    public void setWaitlistIdList(List<String> waitlistIdList) {
        this.waitlistIdList = waitlistIdList;
    }

}
