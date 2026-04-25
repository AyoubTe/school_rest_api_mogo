package net.samiayoub.school_mogodb.controller;

import net.samiayoub.school_mogodb.dto.requets.StudentRequest;
import net.samiayoub.school_mogodb.dto.responses.CourseResponse;
import net.samiayoub.school_mogodb.dto.responses.GradeDetailsResponse;
import net.samiayoub.school_mogodb.dto.responses.StudentResponse;
import net.samiayoub.school_mogodb.mapper.StudentMapper;
import net.samiayoub.school_mogodb.service.StudentService;
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
@RequestMapping("/api/v1/students")
public class StudentController {
    private final StudentService studentService;
    private final StudentMapper studentMapper;

    public StudentController(StudentService studentService, StudentMapper studentMapper) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
    }

    @GetMapping
    public List<StudentResponse> getStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponse addStudent(@RequestBody StudentRequest studentRequest) {
        return studentService.createStudent(studentMapper.toEntity(studentRequest));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponse updateStudent(@RequestBody StudentRequest studentRequest) {
        return studentService.updateStudent(studentMapper.toEntity(studentRequest));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable String id) {
        studentService.deleteStudentById(id);
    }

    @GetMapping("/{id}")
    public StudentResponse getStudentById(@PathVariable String id) {
        return studentService.getStudentById(id);
    }

    @GetMapping("/{id}/courses")
    public List<CourseResponse> getCourses(@PathVariable String id) {
        return studentService.getCourses(id);
    }

    @GetMapping("/{id}/grades")
    public List<GradeDetailsResponse> getGradesForStudent(@PathVariable String id) {
        return studentService.getGrades(id);
    }

    @GetMapping("/myprofile")
    public StudentResponse getMyProfile(Principal principal) {
        return studentService.getStudentByUsername(principal.getName());
    }

    @GetMapping("/mycourses")
    public List<CourseResponse> getMyCourses(Principal principal) {
        return studentService.getCoursesByStudentUsername(principal.getName());
    }

    @GetMapping("/mygrades")
    public List<GradeDetailsResponse> getMyGrades(Principal principal) {
        return studentService.getGradesByStudentUsername(principal.getName());
    }
}
