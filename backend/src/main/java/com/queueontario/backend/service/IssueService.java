package com.queueontario.backend.service;

import com.queueontario.backend.models.Issue;
import com.queueontario.backend.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    public Issue saveIssue(Issue issue) {
        issue.setCreatedAt(LocalDateTime.now());
        return issueRepository.save(issue);
    }
}
