package net.samiayoub.school_mogodb.service;

import net.samiayoub.school_mogodb.dto.responses.AssignmentResponse;
import net.samiayoub.school_mogodb.entity.Assignment;
import net.samiayoub.school_mogodb.exception.ResourceNotFoundException;
import net.samiayoub.school_mogodb.mapper.AssignmentMapper;
import net.samiayoub.school_mogodb.repository.AssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper assignmentMapper;

    public AssignmentService(AssignmentRepository assignmentRepository, AssignmentMapper assignmentMapper) {
        this.assignmentRepository = assignmentRepository;
        this.assignmentMapper = assignmentMapper;
    }

    public AssignmentResponse createAssignment(Assignment assignment) {
        return assignmentMapper.toDto(assignmentRepository.save(assignment));
    }

    public AssignmentResponse updateAssignment(Assignment assignment) {
        return assignmentMapper.toDto(assignmentRepository.save(assignment));
    }

    public void deleteAssignment(Assignment assignment) {
        assignmentRepository.delete(assignment);
    }

    public void deleteById(String id) {
        assignmentRepository.deleteById(id);
    }

    public List<AssignmentResponse> getAllAssignments() {
        return assignmentMapper.toDtoList(assignmentRepository.findAll());
    }

    public AssignmentResponse getAssignmentById(String id) {
        Assignment assignment = assignmentRepository.findById(id).orElse(null);
        if (assignment == null) {
            throw new ResourceNotFoundException("Assignment with id: " + id + " not found");
        }
        return assignmentMapper.toDto(assignment);
    }
}
