package com.queueontario.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.queueontario.backend.models.UserWaitList;
import java.util.Optional;

public interface UserWaitlistRepo extends MongoRepository<UserWaitList, String>{
    UserWaitList findByUserId(String userId);

     Optional<UserWaitList> findUserByUserId(String userId);
}
