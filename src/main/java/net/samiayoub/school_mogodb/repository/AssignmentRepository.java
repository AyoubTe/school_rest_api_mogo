package net.samiayoub.school_mogodb.repository;

import net.samiayoub.school_mogodb.entity.Assignment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssignmentRepository extends MongoRepository<Assignment,String> {
}
