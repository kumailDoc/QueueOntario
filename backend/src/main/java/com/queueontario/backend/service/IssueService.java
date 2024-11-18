package com.queueontario.backend.service;

import com.queueontario.backend.models.Issue;
import com.queueontario.backend.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    public void saveIssue(Issue issue) {
        issue.setCreatedAt(LocalDateTime.now()); // Set the creation timestamp
        issueRepository.save(issue);
    }

    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    // Find an issue by its ID
    public Optional<Issue> findIssueById(String id) {
        return issueRepository.findById(id); // Returns an Optional
    }

    public void deleteIssueById(String id) {
        issueRepository.deleteById(id);
    }


}