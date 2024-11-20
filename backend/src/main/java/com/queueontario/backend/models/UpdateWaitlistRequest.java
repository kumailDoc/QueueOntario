package com.queueontario.backend.models;

import java.util.List;

public class UpdateWaitlistRequest {
    private String waitlistId;
    private String averageWaitTime; 
    private List<String> removeUserIds; // List of users to remove

    // Getters and setters
    public String getWaitlistId() {
        return waitlistId;
    }

    public void setWaitlistId(String waitlistId) {
        this.waitlistId = waitlistId;
    }

    public String getAverageWaitTime() {
        return averageWaitTime;
    }

    public void setAverageWaitTime(String averageWaitTime) {
        this.averageWaitTime = averageWaitTime;
    }

    public List<String> getRemoveUserIds() {
        return removeUserIds;
    }

    public void setRemoveUserIds(List<String> removeUserIds) {
        this.removeUserIds = removeUserIds;
    }
}
