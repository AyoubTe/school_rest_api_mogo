package net.samiayoub.school_mogodb.controller;

import net.samiayoub.school_mogodb.dto.requets.SchoolRequest;
import net.samiayoub.school_mogodb.dto.responses.SchoolResponse;
import net.samiayoub.school_mogodb.entity.School;
import net.samiayoub.school_mogodb.mapper.SchoolMapper;
import net.samiayoub.school_mogodb.service.SchoolService;
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
@RequestMapping("/api/v1/schools")
public class SchoolController {
    private final SchoolService schoolService;
    private final SchoolMapper schoolMapper;

    public SchoolController(SchoolService schoolService, SchoolMapper schoolMapper) {
        this.schoolService = schoolService;
        this.schoolMapper = schoolMapper;
    }

    @GetMapping
    public List<SchoolResponse> getSchools() {
        return schoolService.getSchools();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SchoolResponse addSchool(@RequestBody SchoolRequest schoolRequest) {
        return schoolService.createSchool(schoolMapper.toEntity(schoolRequest));
    }

    @PutMapping("/{id}")
    public SchoolResponse updateSchool(@RequestBody SchoolRequest schoolRequest,  @PathVariable String id) {
        School school = schoolMapper.toEntity(schoolRequest);
        school.setId(id);
        return schoolService.updateSchool(school);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchool(@PathVariable String id) {
        schoolService.deleteSchoolById(id);
    }

    @GetMapping("/{id}")
    public SchoolResponse getSchool(@PathVariable String id) {
        return schoolService.findSchoolById(id);
    }
}
