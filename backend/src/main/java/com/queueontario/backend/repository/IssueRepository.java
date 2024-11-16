package com.queueontario.backend.repository;

import com.queueontario.backend.models.Issue;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IssueRepository extends MongoRepository<Issue, String> {
}
