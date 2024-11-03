package com.queueontario.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.queueontario.backend.models.Waitlist;

public interface WaitlistRepo extends MongoRepository<Waitlist, String> {
    Waitlist findWaitlistByWaitlistId(String waitlistId);
}