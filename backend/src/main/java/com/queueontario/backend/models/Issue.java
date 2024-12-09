package com.queueontario.backend.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

/**
 * Represent an issue 
 * The issue contains fields such as name, issue, comments, and email
 * that's submitted by a user.
 * It provides getters and setters for these fields
 * and automatically set the time for CreatedDate when a document is saved
 */
@Document(collection = "issues")
public class Issue {

    @Id
    private String id;
    private String name;
    private String issue;
    private String comments;
    private String email;

    @CreatedDate // Automatically sets the creation time when a document is saved
    private LocalDateTime createdAt;

    /**
     * get and set the user id
     * @return user id
     */
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    /**
     * get and set name
     * @return name
     */
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    /**
     * get and set issue
     * @return issue
     */
    public String getIssue() { return issue; }
    public void setIssue(String issue) { this.issue = issue; }

    /**
     * get and set comments
     * @return comments
     */
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    /**
     * get and set email
     * @return email
     */
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    /**
     * get and set time issue is created at
     * @return time issue is created at
     */
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}