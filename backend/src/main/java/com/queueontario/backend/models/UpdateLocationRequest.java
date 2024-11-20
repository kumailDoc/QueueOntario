package com.queueontario.backend.models;

//DTO class for UpdateLocation
public class UpdateLocationRequest {
    private String locationId; // ID of the location
    private String address;    // New address

    // Getters and setters
    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

