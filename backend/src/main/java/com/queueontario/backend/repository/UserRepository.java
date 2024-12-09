package com.queueontario.backend.repository;

import com.queueontario.backend.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link User} document in MongoDB
 * his interface extends {@link MongoRepository} and provides methods for CRUD operations,
 * and contains custom queries related to {@link User}.
 */
public interface UserRepository extends MongoRepository<User, String> {
    
    /**
     * Find a {@link User} by their username
     * This method uses the MongoDB query derived from the method name.
     * 
     * @param username - username of the user to search for
     * @return an optional containing the user if found, or an empty optional if no user is found
     */
    Optional<User> findByUsername(String username);

    /**
     * Find a {@link User} by their username
     * This method uses the MongoDB query derived from the method name.
     * @param username - username of the user to search for
     * @return a boolean if the user's username is found or not
     */
    Boolean existsByUsername(String username);

    /**
     * Find a {@link User} by their username
     * This method uses the MongoDB query derived from the method name.
     * 
     * @param email - email of the user to search for
     * @returna boolean if the user's email is found or not
     */
    Boolean existsByEmail(String email);
}
