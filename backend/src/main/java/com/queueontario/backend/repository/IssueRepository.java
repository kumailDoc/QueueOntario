package com.queueontario.backend.repository;

import com.queueontario.backend.models.Issue;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for accessing and managing {@link Issue} documents in
 * MongoDB
 * This interface extends {@link MongoRepository}, which provides basic CRUD
 * operations
 * No custom methods are defined for this repository
 */
public interface IssueRepository extends MongoRepository<Issue, String> {
}
