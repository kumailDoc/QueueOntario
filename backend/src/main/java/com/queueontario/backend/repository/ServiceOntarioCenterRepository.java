package com.queueontario.backend.repository;

import com.queueontario.backend.models.ServiceOntarioCenter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ServiceOntarioCenterRepository extends MongoRepository<ServiceOntarioCenter, String> {
    
    Optional<ServiceOntarioCenter> findById(String id);
    List<ServiceOntarioCenter> findByCity(String city);
}
