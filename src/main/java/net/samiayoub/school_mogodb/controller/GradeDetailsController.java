package net.samiayoub.school_mogodb.controller;

import net.samiayoub.school_mogodb.dto.requets.GradeDetailsRequest;
import net.samiayoub.school_mogodb.dto.responses.GradeDetailsResponse;
import net.samiayoub.school_mogodb.mapper.GradeDetailsMapper;
import net.samiayoub.school_mogodb.service.GradeDetailsService;
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
@RequestMapping("/api/v1/gradeDetails")
public class GradeDetailsController {
    private final GradeDetailsService gradeDetailsService;
    private final GradeDetailsMapper gradeDetailsMapper;

    public GradeDetailsController(GradeDetailsService gradeDetailsService, GradeDetailsMapper gradeDetailsMapper) {
        this.gradeDetailsService = gradeDetailsService;
        this.gradeDetailsMapper = gradeDetailsMapper;
    }

    @GetMapping
    public List<GradeDetailsResponse> getGradeDetails() {
        return gradeDetailsService.findAllGradeDetails();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GradeDetailsResponse getGradeDetails(@RequestBody GradeDetailsRequest gradeDetailsRequest) {
        return gradeDetailsService.createGradeDetails(gradeDetailsMapper.toEntity(gradeDetailsRequest));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GradeDetailsResponse updateGradeDetails(@RequestBody GradeDetailsRequest gradeDetailsRequest) {
        return gradeDetailsService.updateGradeDetails(gradeDetailsMapper.toEntity(gradeDetailsRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGradeDetails(@PathVariable String id) {
        gradeDetailsService.deleteGradeDetailsById(id);
    }

    @GetMapping("/{id}")
    public GradeDetailsResponse getGradeDetails(@PathVariable String id) {
        return gradeDetailsService.findGradeDetailsById(id);
    }
}
