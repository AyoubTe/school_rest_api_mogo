package net.samiayoub.school_mogodb.repository;


import net.samiayoub.school_mogodb.entity.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TeacherRepository extends MongoRepository<Teacher,String> {
    Optional<Teacher> findByUsername(String username);
    Optional<Teacher> findByEmail(String email);
    Optional<Teacher> findByUsernameOrEmail(String username, String email);
}
