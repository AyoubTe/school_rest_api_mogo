package net.samiayoub.school_mogodb.controller;

import net.samiayoub.school_mogodb.dto.requets.TeacherRequest;
import net.samiayoub.school_mogodb.dto.responses.CourseResponse;
import net.samiayoub.school_mogodb.dto.responses.SchoolResponse;
import net.samiayoub.school_mogodb.dto.responses.TeacherResponse;
import net.samiayoub.school_mogodb.mapper.TeacherMapper;
import net.samiayoub.school_mogodb.service.TeacherService;
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

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/teachers")
public class TeacherController {
    private final TeacherService teacherService;
    private final TeacherMapper teacherMapper;

    public TeacherController(TeacherService teacherService, TeacherMapper teacherMapper) {
        this.teacherService = teacherService;
        this.teacherMapper = teacherMapper;
    }

    @GetMapping
    public List<TeacherResponse> getTeachers() {
        return teacherService.getAllTeachers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherResponse addTeacher(@RequestBody TeacherRequest teacherRequest) {
        return teacherService.createTeacher(teacherMapper.toEntity(teacherRequest));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherResponse updateTeacher(@RequestBody TeacherRequest teacherRequest) {
        return teacherService.updateTeacher(teacherMapper.toEntity(teacherRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeacher(@PathVariable String id) {
        teacherService.deleteTeacherById(id);
    }

    @GetMapping("/{id}")
    public TeacherResponse getTeacher(@PathVariable String id) {
        return teacherService.getTeacherById(id);
    }

    @GetMapping("/{id}/courses")
    public List<CourseResponse> getCourses(@PathVariable String id) {
        return teacherService.getCoursesByTeacherId(id);
    }

    @GetMapping("/{id}/school")
    public SchoolResponse getSchool(@PathVariable String id) {
        return teacherService.getSchool(id);
    }

    @GetMapping("/myprofile")
    public TeacherResponse getMyProfile(Principal principal) {
        return teacherService.getTeacherByUsername(principal.getName());
    }

    @GetMapping("/mycourses")
    public List<CourseResponse> getMyCourses(Principal principal) {
        return teacherService.getCoursesByTeacherUsername(principal.getName());
    }

    @GetMapping("/myschool")
    public SchoolResponse getMySchool(Principal principal) {
        return teacherService.getSchoolByUsername(principal.getName());
    }
}
