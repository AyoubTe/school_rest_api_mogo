package net.samiayoub.school_mogodb.repository;

import net.samiayoub.school_mogodb.entity.AssignmentDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssignmentDetailsRepository extends MongoRepository<AssignmentDetails,String> {
}
