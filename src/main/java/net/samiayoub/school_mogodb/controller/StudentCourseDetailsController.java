package net.samiayoub.school_mogodb.controller;

import net.samiayoub.school_mogodb.dto.requets.StudentCourseDetailsRequest;
import net.samiayoub.school_mogodb.dto.responses.StudentCourseDetailsResponse;
import net.samiayoub.school_mogodb.mapper.StudentCourseDetailsMapper;
import net.samiayoub.school_mogodb.service.StudentCourseDetailsService;
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
@RequestMapping("/api/v1/studentCourseDetails")
public class StudentCourseDetailsController {
    private final StudentCourseDetailsService studentCourseDetailsService;
    private final StudentCourseDetailsMapper studentCourseDetailsMapper;

    public StudentCourseDetailsController(StudentCourseDetailsService studentCourseDetailsService, StudentCourseDetailsMapper studentCourseDetailsMapper) {
        this.studentCourseDetailsService = studentCourseDetailsService;
        this.studentCourseDetailsMapper = studentCourseDetailsMapper;
    }

    @GetMapping
    public List<StudentCourseDetailsResponse> getStudentCourseDetails() {
        return studentCourseDetailsService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentCourseDetailsResponse addStudentCourseDetails(@RequestBody StudentCourseDetailsRequest studentCourseDetailsRequest) {
        return studentCourseDetailsService.create(studentCourseDetailsMapper.toEntity(studentCourseDetailsRequest));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentCourseDetailsResponse updateStudentCourseDetails(@RequestBody StudentCourseDetailsRequest studentCourseDetailsRequest) {
        return studentCourseDetailsService.update(studentCourseDetailsMapper.toEntity(studentCourseDetailsRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudentCourseDetails(@PathVariable String id) {
        studentCourseDetailsService.deleteById(id);
    }

    @GetMapping("/{id}")
    public StudentCourseDetailsResponse getStudentCourseDetails(@PathVariable String id) {
        return studentCourseDetailsService.findById(id);
    }
}
