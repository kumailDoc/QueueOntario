package com.queueontario.backend.repository;

import com.queueontario.backend.models.ERole;
import com.queueontario.backend.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Role} documents in MongoDB
 * This interface extends {@link MongoRepository}, which provides basic CRUD
 * operations
 * and contains custom queries related to {@link Role}
 */
public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
