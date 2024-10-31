package backend.src.main.java.repository;

import models.ERole;
import models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
