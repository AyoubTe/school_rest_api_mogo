package net.samiayoub.school_mogodb.repository;

import net.samiayoub.school_mogodb.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student,String> {
    Optional<Student> findByUsername(String username);
    Optional<Student> findByEmail(String emailOrUsername);
    Optional<Student> findByUsernameOrEmail(String username, String email);
}
