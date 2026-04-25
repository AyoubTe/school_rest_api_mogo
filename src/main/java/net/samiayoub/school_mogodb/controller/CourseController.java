package net.samiayoub.school_mogodb.controller;

import net.samiayoub.school_mogodb.dto.requets.CourseRequest;
import net.samiayoub.school_mogodb.dto.responses.CourseResponse;
import net.samiayoub.school_mogodb.dto.responses.StudentResponse;
import net.samiayoub.school_mogodb.dto.responses.TeacherResponse;
import net.samiayoub.school_mogodb.entity.Course;
import net.samiayoub.school_mogodb.entity.Teacher;
import net.samiayoub.school_mogodb.mapper.CourseMapper;
import net.samiayoub.school_mogodb.service.CourseService;
import net.samiayoub.school_mogodb.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/api/v1/courses")
public class CourseController {
    private final CourseService courseService;
    private final CourseMapper courseMapper;
    private final TeacherService teacherService;

    public CourseController(CourseService courseService, CourseMapper courseMapper, TeacherService teacherService) {
        this.courseService = courseService;
        this.courseMapper = courseMapper;
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<CourseResponse> getCourses() {
        return courseService.findAllCourses();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse addCourse(@RequestBody CourseRequest courseRequest) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        Teacher teacher = teacherService.getTeacherByUsernameOrEmail(principal.getName());
        Course course = courseMapper.toEntity(courseRequest);
        course.setTeacher(teacher);
        return courseService.createCourse(course);
    }

    @PutMapping
    public CourseResponse updateCourse(@RequestBody CourseResponse courseRequest) {
        Course course = new Course();
        course.setId(courseRequest.id());
        course.setCode(courseRequest.code());
        course.setName(courseRequest.name());
        return courseService.updateCourse(course);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable String id) {
        courseService.deleteCourseById(id);
    }

    @GetMapping("/{id}")
    public CourseResponse getCourse(@PathVariable String id) {
        return courseService.findCourseById(id);
    }

    @GetMapping("/{id}/students")
    public List<StudentResponse> getStudents(@PathVariable String id) {
        return courseService.getEnrolledStudents(id);
    }

    @GetMapping("/{id}/teacher")
    public TeacherResponse getTeacher(@PathVariable String id) {
        return courseService.getTeacher(id);
    }
}
