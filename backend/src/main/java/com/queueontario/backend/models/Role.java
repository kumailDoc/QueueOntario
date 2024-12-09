package com.queueontario.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a Role in the system
 * This class contains the role's id and name.
 * It provides getters and setters for these fields and
 * a utility method
 */
@Document(collection = "roles")
public class Role {
    @Id
    private String id;

    private ERole name;

    /**
     * default constructor
     */
    public Role() {

    }

    /**
     * gets the role's name
     * 
     * @param name
     */
    public Role(ERole name) {
        this.name = name;
    }

    /**
     * gets the role's Id
     * 
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * sets the role's id
     * 
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * gets the role's name
     * 
     * @return name
     */
    public ERole getName() {
        return name;
    }

    /**
     * sets the role's name
     * 
     * @param name
     */
    public void setName(ERole name) {
        this.name = name;
    }
}
