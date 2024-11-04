package com.queueontario.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.queueontario.backend.models.Waitlist;
import java.util.Optional;

public interface WaitlistRepo extends MongoRepository<Waitlist, String> {
    Waitlist findWaitlistByWaitlistId(String waitlistId);
    Optional<Waitlist> findById(String waitlistId);
    Optional<Waitlist> findByLocationId(String locationId);
}