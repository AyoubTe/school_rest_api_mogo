package net.samiayoub.school_mogodb.service;

import net.samiayoub.school_mogodb.dto.responses.AssignmentDetailsResponse;
import net.samiayoub.school_mogodb.entity.AssignmentDetails;
import net.samiayoub.school_mogodb.exception.ResourceNotFoundException;
import net.samiayoub.school_mogodb.mapper.AssignmentDetailsMapper;
import net.samiayoub.school_mogodb.repository.AssignmentDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentDetailsService {
    private final AssignmentDetailsRepository assignmentDetailsRepository;
    private final AssignmentDetailsMapper assignmentDetailsMapper;

    public AssignmentDetailsService(AssignmentDetailsRepository assignmentDetailsRepository, AssignmentDetailsMapper assignmentDetailsMapper) {
        this.assignmentDetailsRepository = assignmentDetailsRepository;
        this.assignmentDetailsMapper = assignmentDetailsMapper;
    }

    public AssignmentDetailsResponse createAssignmentDetails(AssignmentDetails assignmentDetails) {
        return assignmentDetailsMapper.toDto(assignmentDetailsRepository.save(assignmentDetails));
    }

    public AssignmentDetailsResponse updateAssignmentDetails(AssignmentDetails assignmentDetails) {
        return assignmentDetailsMapper.toDto(assignmentDetailsRepository.save(assignmentDetails));
    }

    public void deleteAssignmentDetails(AssignmentDetails assignmentDetails) {
        assignmentDetailsRepository.delete(assignmentDetails);
    }

    public void deleteAssignmentDetailsById(String id) {
        assignmentDetailsRepository.deleteById(id);
    }

    public List<AssignmentDetailsResponse> findAll() {
        List<AssignmentDetails> assignmentDetails = assignmentDetailsRepository.findAll();
        return assignmentDetailsMapper.toDtoList(assignmentDetails);
    }

    public AssignmentDetailsResponse findById(String id) {
        AssignmentDetails assignmentDetails = assignmentDetailsRepository.findById(id).orElse(null);
        if (assignmentDetails == null) {
            throw new ResourceNotFoundException("Assignment not found with id: " + id);
        }
        return assignmentDetailsMapper.toDto(assignmentDetails);
    }
}
