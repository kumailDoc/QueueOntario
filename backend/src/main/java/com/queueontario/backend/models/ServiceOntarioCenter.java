package com.queueontario.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "serviceontario_centers")
public class ServiceOntarioCenter {
    @Id
    private String id;
    private String name;
    private String address;
    private String city;
    private String waitlistId;

    // Constructors, getters, and setters
    public ServiceOntarioCenter() {}

    public ServiceOntarioCenter(String name, String address, String city, String waitlistId) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.waitlistId = waitlistId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWaitlistId() {
        return waitlistId;
    }
    public void setWaitlistId(String waitlistId) {
        this.waitlistId = waitlistId;
    }
}
