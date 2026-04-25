package net.samiayoub.school_mogodb.service;

import net.samiayoub.school_mogodb.dto.responses.CourseResponse;
import net.samiayoub.school_mogodb.dto.responses.GradeDetailsResponse;
import net.samiayoub.school_mogodb.dto.responses.StudentResponse;
import net.samiayoub.school_mogodb.entity.GradeDetails;
import net.samiayoub.school_mogodb.entity.Student;
import net.samiayoub.school_mogodb.exception.ResourceNotFoundException;
import net.samiayoub.school_mogodb.mapper.CourseMapper;
import net.samiayoub.school_mogodb.mapper.GradeDetailsMapper;
import net.samiayoub.school_mogodb.mapper.StudentMapper;
import net.samiayoub.school_mogodb.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;
    private final GradeDetailsMapper gradeDetailsMapper;
    private final GradeDetailsService gradeDetailsService;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper, CourseMapper courseMapper, GradeDetailsMapper gradeDetailsMapper, GradeDetailsService gradeDetailsService) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.courseMapper = courseMapper;
        this.gradeDetailsMapper = gradeDetailsMapper;
        this.gradeDetailsService = gradeDetailsService;
    }

    public StudentResponse createStudent(Student student) {
        return studentMapper.toDto(studentRepository.save(student));
    }

    public StudentResponse updateStudent(Student student) {
        return studentMapper.toDto(studentRepository.save(student));
    }

    public void deleteStudent(Student student) {
        studentRepository.delete(student);
    }

    public void deleteStudentById(String id) {
        studentRepository.deleteById(id);
    }

    public List<StudentResponse> getAllStudents() {
        return studentMapper.toDtoList(studentRepository.findAll());
    }

    public StudentResponse getStudentById(String id) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) {
            throw new ResourceNotFoundException("Student with id: " + id + " not found");
        }
        return studentMapper.toDto(student);
    }

    public List<CourseResponse> getCourses(String id) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) {
            throw new ResourceNotFoundException("Student with id: " + id + " not found");
        }
        return courseMapper.toDtoList(student.getCourses());
    }

    public List<GradeDetailsResponse>  getGrades(String id) {
        List<GradeDetails> grades = gradeDetailsService.getGradesForStudent(id);
        return gradeDetailsMapper.toDtoList(grades);
    }

    public boolean isStudentExists(String emailOrUsername) {
        return (studentRepository.findByUsername(emailOrUsername).orElse(null) != null) || (studentRepository.findByEmail(emailOrUsername).orElse(null) != null);
    }

    public Student getStudentByUsernameOrEmail(String usernameOrEmail) {
        return studentRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElse(null);
    }

    public StudentResponse getStudentByUsername(String name) {
        Student student = getStudentByUsernameOrEmail(name);
        if (student == null) {
            throw new ResourceNotFoundException("Student with name: " + name + " not found");
        }
        return studentMapper.toDto(student);
    }

    public List<CourseResponse> getCoursesByStudentUsername(String name) {
        Student student = getStudentByUsernameOrEmail(name);
        if (student == null) {
            throw new ResourceNotFoundException("Student with name: " + name + " not found");
        }
        return courseMapper.toDtoList(student.getCourses());
    }

    public List<GradeDetailsResponse> getGradesByStudentUsername(String name) {
        Student student = getStudentByUsernameOrEmail(name);
        if (student == null) {
            throw new ResourceNotFoundException("Student with name: " + name + " not found");
        }
        List<GradeDetails> grades = gradeDetailsService.getGradesForStudent(student.getId());
        return gradeDetailsMapper.toDtoList(grades);
    }
}
