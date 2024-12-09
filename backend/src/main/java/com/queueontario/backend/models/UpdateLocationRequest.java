package com.queueontario.backend.models;

/**
 * A data transfer object (DTO) representing the details
 * of a service ontario location.
 * This class is used to transer user data between different layers on the app.
 * It provides getters and setters for these fields.
 */

public class UpdateLocationRequest {
    private String locationId; // ID of the location
    private String address; // New address

    /**
     * gets the unique ID of the location
     * 
     * @return locationId
     */
    public String getLocationId() {
        return locationId;
    }

    /**
     * sets the unique ID of the lcoation
     * 
     * @param locationId
     */
    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    /**
     * gets the address of the location
     * 
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * sets the address of the location
     * 
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
