package net.samiayoub.school_mogodb.repository;


import net.samiayoub.school_mogodb.entity.School;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SchoolRepository extends MongoRepository<School,String> {
}
