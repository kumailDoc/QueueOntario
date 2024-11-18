package com.queueontario.backend.controllers;

import com.queueontario.backend.models.Issue;
import com.queueontario.backend.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/report")
public class IssueController {

    @Autowired
    private IssueService issueService;

    // Endpoint for reporting an issue
    @PostMapping
    public ResponseEntity<String> reportIssue(@RequestBody Issue issue) {
        issueService.saveIssue(issue);
        return ResponseEntity.ok("Issue reported successfully!");
    }

    // Endpoint for retrieving all issues
    @GetMapping
    public ResponseEntity<List<Issue>> getAllIssues() {
        List<Issue> issues = issueService.getAllIssues();
        return ResponseEntity.ok(issues);
    }

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