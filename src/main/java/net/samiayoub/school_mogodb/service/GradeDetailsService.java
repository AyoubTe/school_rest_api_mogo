package net.samiayoub.school_mogodb.service;

import net.samiayoub.school_mogodb.dto.responses.GradeDetailsResponse;
import net.samiayoub.school_mogodb.entity.GradeDetails;
import net.samiayoub.school_mogodb.entity.StudentCourseDetails;
import net.samiayoub.school_mogodb.exception.ResourceNotFoundException;
import net.samiayoub.school_mogodb.mapper.GradeDetailsMapper;
import net.samiayoub.school_mogodb.repository.GradeDetailsRepository;
import net.samiayoub.school_mogodb.repository.StudentCourseDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GradeDetailsService {
    private final GradeDetailsRepository gradeDetailsRepository;
    private final GradeDetailsMapper gradeDetailsMapper;
    private final StudentCourseDetailsRepository detailsRepository;

    public GradeDetailsService(GradeDetailsRepository gradeDetailsRepository, GradeDetailsMapper gradeDetailsMapper, StudentCourseDetailsRepository detailsRepository) {
        this.gradeDetailsRepository = gradeDetailsRepository;
        this.gradeDetailsMapper = gradeDetailsMapper;
        this.detailsRepository = detailsRepository;
    }

    public GradeDetailsResponse createGradeDetails(GradeDetails gradeDetails) {
        return gradeDetailsMapper.toDto(gradeDetailsRepository.save(gradeDetails));
    }

    public GradeDetailsResponse updateGradeDetails(GradeDetails gradeDetails) {
        return gradeDetailsMapper.toDto(gradeDetailsRepository.save(gradeDetails));
    }

    public void deleteGradeDetails(GradeDetails gradeDetails) {
        gradeDetailsRepository.deleteById(gradeDetails.getId());
    }

    public void deleteGradeDetailsById(String id) {
        gradeDetailsRepository.deleteById(id);
    }

    public List<GradeDetailsResponse> findAllGradeDetails() {
        List<GradeDetails> gradeDetails = gradeDetailsRepository.findAll();
        return gradeDetailsMapper.toDtoList(gradeDetails);
    }

    public GradeDetailsResponse findGradeDetailsById(String id) {
        GradeDetails gradeDetails = gradeDetailsRepository.findById(id).orElse(null);
        if (gradeDetails == null) {
            throw new ResourceNotFoundException("GradeDetails not found with id " + id);
        }
        return gradeDetailsMapper.toDto(gradeDetails);
    }

    public List<GradeDetails> getGradesForStudent(String studentId) {
        // 1. On récupère toutes les inscriptions de l'étudiant
        List<StudentCourseDetails> enrollments = detailsRepository.findByStudentId(studentId);

        // 2. On extrait uniquement les bulletins de notes
        return enrollments.stream()
                .map(StudentCourseDetails::getGradeDetails)
                .filter(Objects::nonNull) // On ignore les cours où l'étudiant n'a pas encore de notes
                .collect(Collectors.toList());
    }
}
