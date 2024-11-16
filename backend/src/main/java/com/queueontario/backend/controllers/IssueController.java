package com.queueontario.backend.controllers;

import com.queueontario.backend.models.Issue;
import com.queueontario.backend.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @PostMapping
    public ResponseEntity<String> reportIssue(@RequestBody Issue issue) {
        issueService.saveIssue(issue);
        return ResponseEntity.ok("Issue reported successfully!");
    }
}
