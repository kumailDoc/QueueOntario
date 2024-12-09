package com.queueontario.backend.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a user in the system.
 * This class contains the user's details
 * such as username, email, password, and role.
 * It provides getters and setters for these fields
 * and some utiltiy methods to interact with the user.
 */
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    /**
     * default constructor
     */
    public User() {
    }

    /**
     * constructs a new User with the specified details
     * 
     * @param username
     * @param email
     * @param password
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * gets user id
     * 
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * sets user id
     * 
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * gets username
     * 
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * sets username
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * gets user email
     * 
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets user email
     * 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * gets user password
     * 
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * sets user password
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * gets user role
     * 
     * @return roles
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * sets user role
     * 
     * @param roles
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}