package net.samiayoub.school_mogodb.service;

import net.samiayoub.school_mogodb.dto.responses.StudentCourseDetailsResponse;
import net.samiayoub.school_mogodb.entity.StudentCourseDetails;
import net.samiayoub.school_mogodb.exception.ResourceNotFoundException;
import net.samiayoub.school_mogodb.mapper.StudentCourseDetailsMapper;
import net.samiayoub.school_mogodb.repository.StudentCourseDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCourseDetailsService {
    private final StudentCourseDetailsRepository studentCourseDetailsRepository;
    private final StudentCourseDetailsMapper studentCourseDetailsMapper;

    public StudentCourseDetailsService(StudentCourseDetailsRepository studentCourseDetailsRepository, StudentCourseDetailsMapper studentCourseDetailsMapper) {
        this.studentCourseDetailsRepository = studentCourseDetailsRepository;
        this.studentCourseDetailsMapper = studentCourseDetailsMapper;
    }

    public StudentCourseDetailsResponse create(StudentCourseDetails studentCourseDetails) {
        return studentCourseDetailsMapper.toDto(studentCourseDetailsRepository.save(studentCourseDetails));
    }

    public StudentCourseDetailsResponse update(StudentCourseDetails studentCourseDetails) {
        return studentCourseDetailsMapper.toDto(studentCourseDetailsRepository.save(studentCourseDetails));
    }

    public void deleteById(String id) {
        studentCourseDetailsRepository.deleteById(id);
    }

    public List<StudentCourseDetailsResponse> findAll() {
        List<StudentCourseDetails> studentCourseDetails = studentCourseDetailsRepository.findAll();
        return studentCourseDetailsMapper.toDtoList(studentCourseDetails);
    }

    public StudentCourseDetailsResponse findById(String id) {
        StudentCourseDetails studentCourseDetails = studentCourseDetailsRepository.findById(id).orElse(null);
        if (studentCourseDetails == null) {
            throw new ResourceNotFoundException("No student course details found with id: " + id);
        }
        return studentCourseDetailsMapper.toDto(studentCourseDetails);
    }
}
