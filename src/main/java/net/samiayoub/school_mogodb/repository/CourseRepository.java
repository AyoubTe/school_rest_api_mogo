package net.samiayoub.school_mogodb.repository;


import net.samiayoub.school_mogodb.entity.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CourseRepository extends MongoRepository<Course,String> {
    Optional<Course> findByCode(String code);
}
