package net.samiayoub.school_mogodb.repository;

import net.samiayoub.school_mogodb.entity.StudentCourseDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StudentCourseDetailsRepository extends MongoRepository<StudentCourseDetails, String> {
    List<StudentCourseDetails> findByStudentId(String studentId);
}
