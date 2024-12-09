package com.queueontario.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a service ontario center in the system
 * This class contains the center's name, address, city, and waitlist id.
 * It provides getters and setters for these fields and
 * some utility methods
 */
@Document(collection = "serviceontario_centers")
public class ServiceOntarioCenter {
    @Id
    private String id;
    private String name;
    private String address;
    private String city;
    private String waitlistId;

    /**
     * default constructor
     */
    public ServiceOntarioCenter() {
    }

    /**
     * constructs a new service ontario center with the specified details
     * 
     * @param name       of the service ontario center
     * @param address    of the service ontario center
     * @param city       of the service ontario center
     * @param waitlistId of the service ontario center
     */
    public ServiceOntarioCenter(String name, String address, String city, String waitlistId) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.waitlistId = waitlistId;
    }

    /**
     * gets the service ontario center's unique ID
     * 
     * @return the service ontario center's unique ID
     */
    public String getId() {
        return id;
    }

    /**
     * sets the service ontario center's unique ID
     * 
     * @param id - the service ontario center's unique ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * gets the service ontario center's name
     * 
     * @return service ontario center's name
     */
    public String getName() {
        return name;
    }

    /**
     * sets service ontario center's name
     * 
     * @param name - service ontario center's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets the service ontario center's address
     * 
     * @return adress of the service ontario center
     */
    public String getAddress() {
        return address;
    }

    /**
     * sets the service ontario center's address
     * 
     * @param address of the service ontario center
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * gets the service ontario center city
     * 
     * @return city of the service ontario center
     */
    public String getCity() {
        return city;
    }

    /**
     * sets the service ontario center's city
     * 
     * @param city of the service ontario center
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * gets the service ontario center's waitlist id
     * 
     * @return waitlistId of the service ontario center
     */
    public String getWaitlistId() {
        return waitlistId;
    }

    /**
     * sets the service ontario center's waitlistId
     * 
     * @param waitlistId of the service ontario center
     */
    public void setWaitlistId(String waitlistId) {
        this.waitlistId = waitlistId;
    }
}
