package com.queueontario.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.queueontario.backend.models.Waitlist;
import java.util.Optional;

/**
 * Repository interface for managing {@link Waitlist} documents in MongoDB.
 * This interface extends {@link MongoRepository} and provides methods for CRUD operations,
 * and contains custom queries related to {@link Waitlist}.
 */
public interface WaitlistRepo extends MongoRepository<Waitlist, String> {
    
    /**
     * Finds a {@link Waitlist} by its id
     * This method uses the MongoDB query derived from the method name.
     * 
     * @param waitlistId - id for the waitlist to search for
     * @return a waitlist
     */
    Waitlist findWaitlistByWaitlistId(String waitlistId);
    
    /**
     * Finds a {@link Waitlist} by its id
     * his method uses the MongoDB query derived from the method name.
     * 
     * @param waitlistId - id for the waitlist to search for
     * @return an optional containing the waitlist if found or an empty optional if waitlist is not found
     */
    Optional<Waitlist> findById(String waitlistId);
    
    /**
     * Finds a {@link Waitlist} by its location id
     * his method uses the MongoDB query derived from the method name.
     * 
     * @param locationId - a location id for the waitlist to search for
     * @return an optional containing the waitlist if found or an empty optional if waitlist is not found
     */
    Optional<Waitlist> findByLocationId(String locationId);
}