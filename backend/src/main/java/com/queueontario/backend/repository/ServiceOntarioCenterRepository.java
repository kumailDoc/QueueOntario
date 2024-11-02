package com.queueontario.backend.repository;

import com.queueontario.backend.models.ServiceOntarioCenter;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ServiceOntarioCenterRepository extends MongoRepository<ServiceOntarioCenter, String> {
    List<ServiceOntarioCenter> findByCity(String city);
}
