package net.samiayoub.school_mogodb.repository;


import net.samiayoub.school_mogodb.entity.GradeDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GradeDetailsRepository extends MongoRepository<GradeDetails,String> {
}
