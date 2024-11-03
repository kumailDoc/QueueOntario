package com.queueontario.backend.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="users_waitlist")
public class UserWaitlist {
    
    @Id
    private String userWaitlistId;
    
    private String userId;

    private List<String> waitlistIdList;

    public String getUserWaitlistId() {
        return userWaitlistId;
    }

    public void setUserWaitlistId(String userWaitlistId) {
        this.userWaitlistId = userWaitlistId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getWaitlistIdList() {
        return waitlistIdList;
    }

    public void setWaitlistIdList(List<String> waitlistIdList) {
        this.waitlistIdList = waitlistIdList;
    }

}
