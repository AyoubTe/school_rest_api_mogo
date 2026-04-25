package net.samiayoub.school_mogodb.repository;


import net.samiayoub.school_mogodb.entity.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminRepository extends MongoRepository<Admin, String> {
    Optional<Admin> findByUsername(String username);
    Optional<Admin> findByUsernameOrEmail(String username, String email);
    Optional<Admin> findByEmail(String emailOrUsername);
}
