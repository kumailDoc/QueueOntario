package com.queueontario.backend.repository;

import com.queueontario.backend.models.ERole;
import com.queueontario.backend.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
