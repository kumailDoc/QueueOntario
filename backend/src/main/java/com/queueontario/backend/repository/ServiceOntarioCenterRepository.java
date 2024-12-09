package com.queueontario.backend.repository;

import com.queueontario.backend.models.ServiceOntarioCenter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link ServiceOntarioCenter} documents in MongoDB
 * This interface extends {@link MongoRepository} and provides methods for CRUD operations,
 * and contains customer queries related to {@link ServiceOntarioCenter}
 */
public interface ServiceOntarioCenterRepository extends MongoRepository<ServiceOntarioCenter, String> {
    
    /**
     * Finds a {@link ServiceOntarioCenter} by its id
     * This method uses the MongoDB query derived from the method name.
     * 
     * @param id - the id of the Service Ontario Center
     * @return an optional containing the Service Ontario Center if found
     */ 
    Optional<ServiceOntarioCenter> findById(String id);

    /**
     * Find a list of {@link ServiceOntarioCenter} by its city
     * This method uses the MongoDB query derived from the method name.
     * 
     * @param city - the city of the Service Ontario Center
     * @return a list containing the Service Ontario Center at that city
     */
    List<ServiceOntarioCenter> findByCity(String city);
}
