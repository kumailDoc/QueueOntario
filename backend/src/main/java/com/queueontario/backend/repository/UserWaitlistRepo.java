package com.queueontario.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.queueontario.backend.models.UserWaitlist;

public interface UserWaitlistRepo extends MongoRepository<UserWaitlist, String>{
    UserWaitlist findByUserId(String userId);
}
