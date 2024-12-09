package com.queueontario.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.queueontario.backend.models.UserWaitList;
import java.util.Optional;

/**
 * Repository interface for managing {@link UserWaitList} documents in MongoDB.
 * This interface extends {@link MongoRepository} and provides methods for CRUD operations,
 * and contains custom queries related to {@link UserWaitList}.
 */
public interface UserWaitlistRepo extends MongoRepository<UserWaitList, String>{
    
    /**
     * Finds a {@link UserWaitList} by the user id
     * This method uses the MongoDB query derived from the method name.
     * 
     * @param userId - the user's userId
     * @return the user's waitlist
     */
    UserWaitList findByUserId(String userId);

    /**
     * Finds a {@link UserWaitList} by the user id
     * This method uses the MongoDB query derived from the method name.
     * 
     * @param userId - the user's userId
     * @return the user's waitlist
     */
     Optional<UserWaitList> findUserByUserId(String userId);

     /**
      * Deletes a {@link UserWaitList} by user id
      * This method uses the MongoDB query derived from the method name.

      * @param userId - the user's userId
      */
     void deleteByUserId(String userId);

}
