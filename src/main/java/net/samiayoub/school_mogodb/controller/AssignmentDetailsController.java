package net.samiayoub.school_mogodb.controller;

import jakarta.validation.Valid;
import net.samiayoub.school_mogodb.dto.requets.AssignmentDetailsRequest;
import net.samiayoub.school_mogodb.dto.responses.AssignmentDetailsResponse;
import net.samiayoub.school_mogodb.mapper.AssignmentDetailsMapper;
import net.samiayoub.school_mogodb.service.AssignmentDetailsService;
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
@RequestMapping("/api/v1/assignmentDetails")
public class AssignmentDetailsController {
    private final AssignmentDetailsService assignmentDetailsService;
    private final AssignmentDetailsMapper assignmentDetailsMapper;

    public AssignmentDetailsController(AssignmentDetailsService assignmentDetailsService, AssignmentDetailsMapper assignmentDetailsMapper) {
        this.assignmentDetailsService = assignmentDetailsService;
        this.assignmentDetailsMapper = assignmentDetailsMapper;
    }

    @GetMapping
    public List<AssignmentDetailsResponse> getAssignmentDetails() {
        return assignmentDetailsService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AssignmentDetailsResponse createAssignmentDetails(@Valid @RequestBody AssignmentDetailsRequest assignmentDetailsRequest) {
        return assignmentDetailsService.createAssignmentDetails(assignmentDetailsMapper.toEntity(assignmentDetailsRequest));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AssignmentDetailsResponse updateAssignmentDetails(@Valid @RequestBody AssignmentDetailsRequest assignmentDetailsRequest) {
        return assignmentDetailsService.updateAssignmentDetails(assignmentDetailsMapper.toEntity(assignmentDetailsRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAssignmentDetails(@PathVariable String id) {
        assignmentDetailsService.deleteAssignmentDetailsById(id);
    }

    @GetMapping("/{id}")
    public AssignmentDetailsResponse getAssignmentDetails(@PathVariable String id) {
        return assignmentDetailsService.findById(id);
    }
}
