package com.queueontario.backend.controllers;

import com.queueontario.backend.models.Issue;
import com.queueontario.backend.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for managing user submitted issues related operations.
 * This controller exposes APIs for issue reporting, remove issue by Id,
 * and get all issues details.
 */
@RestController
@RequestMapping("/api/report")
public class IssueController {

    @Autowired
    private IssueService issueService;

    /**
     * Report a new issue with the specified details.
     * This method handles HTTP POST requests to the '/api/report' endpoint.
     * 
     * @param issue the request body containing the user submitted issue details.
     * @return a ResponseEntity containing the issue submission result and status
     */
    @PostMapping
    public ResponseEntity<String> reportIssue(@RequestBody Issue issue) {
        issueService.saveIssue(issue);
        return ResponseEntity.ok("Issue reported successfully!");
    }

    /**
     * Gets all issues as a list for mods.
     * This method handles HTTP GET requests to the '/api/report' endpoint.
     * 
     * @return a ResponseEntity containing the list of issues.
     */
    @GetMapping
    public ResponseEntity<List<Issue>> getAllIssues() {
        List<Issue> issues = issueService.getAllIssues();
        return ResponseEntity.ok(issues);
    }

    /**
     * Deletes an issue by its ID.
     * This method handles HTTP DELETE request to the '/api/report/{id}' endpoint.
     * 
     * @param id the issue's id
     * @return a ResponseEntity containing the result and status
     */
    // DELETE endpoint to remove an issue by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIssue(@PathVariable String id) {
        Optional<Issue> issue = issueService.findIssueById(id); // Check if issue exists
        if (issue.isPresent()) {
            issueService.deleteIssueById(id);
            return ResponseEntity.ok("Issue deleted successfully!");
        } else {
            return ResponseEntity.status(404).body("Issue not found with ID: " + id);
        }
    }
}