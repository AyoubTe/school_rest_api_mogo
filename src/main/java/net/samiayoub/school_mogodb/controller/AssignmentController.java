package net.samiayoub.school_mogodb.controller;

import io.swagger.v3.oas.annotations.Operation;
import net.samiayoub.school_mogodb.dto.requets.AssignmentRequest;
import net.samiayoub.school_mogodb.dto.responses.AssignmentResponse;
import net.samiayoub.school_mogodb.entity.Assignment;
import net.samiayoub.school_mogodb.mapper.AssignmentMapper;
import net.samiayoub.school_mogodb.service.AssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assignments")
public class AssignmentController {
    private final AssignmentService assignmentService;
    private final AssignmentMapper assignmentMapper;

    public AssignmentController(AssignmentService assignmentService, AssignmentMapper assignmentMapper) {
        this.assignmentService = assignmentService;
        this.assignmentMapper = assignmentMapper;
    }

    /**
     * Endpoints to get all assignments
     * @return List<AssignmentResponse>
     */
    @GetMapping
    public List<AssignmentResponse> getAssignments() {
        return assignmentService.getAllAssignments();
    }

    /**
     * Endpoint to create an assignment
     * @param assignmentRequest
     * @return AssignmentResponse
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation (
            summary = "Create an assignment",
            description = "Takes an assignment as an parameter and save it on the db"
    )
    public AssignmentResponse createAssignment(@RequestBody AssignmentRequest assignmentRequest) {
        return assignmentService.createAssignment(assignmentMapper.toEntity(assignmentRequest));
    }

    /**
     * Endpoint to update an assignment
     * @param assignmentRequest
     * @param id
     * @return AssignmentResponse
     */
    @PutMapping("/{id}")
    public AssignmentResponse updateAssignment(@RequestBody AssignmentRequest assignmentRequest, @PathVariable String id) {
        Assignment assignment = assignmentMapper.toEntity(assignmentRequest);
        assignment.setId(id);
        return assignmentService.updateAssignment(assignment);
    }

    /**
     * Endpoint to update an assignment
     * @param id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAssignment(@PathVariable String id) {
        assignmentService.getAssignmentById(id);
    }

    /**
     * Endpoint to get information of an assignment
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public AssignmentResponse getAssigment(@PathVariable String id) {
        return assignmentService.getAssignmentById(id);
    }
}
