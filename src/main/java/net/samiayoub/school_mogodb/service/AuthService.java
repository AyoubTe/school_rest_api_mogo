package net.samiayoub.school_mogodb.service;

import net.samiayoub.school_mogodb.dto.requets.LoginRequest;
import net.samiayoub.school_mogodb.dto.requets.RegisterRequest;
import net.samiayoub.school_mogodb.dto.responses.LoginResponse;
import net.samiayoub.school_mogodb.dto.responses.RegisterResponse;
import net.samiayoub.school_mogodb.entity.Admin;
import net.samiayoub.school_mogodb.entity.School;
import net.samiayoub.school_mogodb.entity.Student;
import net.samiayoub.school_mogodb.entity.Teacher;
import net.samiayoub.school_mogodb.entity.User;
import net.samiayoub.school_mogodb.exception.ResourceNotFoundException;
import net.samiayoub.school_mogodb.repository.SchoolRepository;
import net.samiayoub.school_mogodb.security.jwt.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final AdminService adminService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final SchoolRepository schoolRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthService(PasswordEncoder passwordEncoder, AdminService adminService, StudentService studentService, TeacherService teacherService, SchoolRepository schoolRepository, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.adminService = adminService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.schoolRepository = schoolRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    public LoginResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.emailOrUsername(), request.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        User user = findUserByUsernameOrEmail(request.emailOrUsername());

        return new LoginResponse(jwt, "Login successful", user.getRole().name());
    }

    public RegisterResponse register(RegisterRequest request) {
        RegisterResponse response;

        if (existsByEmail(request.email())) {
            return new RegisterResponse("Email already exists");
        }

        switch (request.role().toUpperCase()) {
            case "STUDENT":
                Student student = new Student();
                student.setUsername(request.username());
                student.setFirstname(request.firstname());
                student.setLastname(request.lastname());
                student.setEmail(request.email());
                student.setCourses(new ArrayList<>());
                student.setPassword(passwordEncoder.encode(request.password()));
                studentService.createStudent(student);
                response = new RegisterResponse("Student Account created successfully");
                break;

            case "ADMIN":
                Admin admin = new Admin();
                admin.setUsername(request.username());
                admin.setFirstname(request.firstname());
                admin.setLastname(request.lastname());
                admin.setEmail(request.email());
                admin.setMission(request.mission());
                admin.setPassword(passwordEncoder.encode(request.password()));
                adminService.registerAdmin(admin);
                response = new RegisterResponse("Admin Account created successfully");
                break;

            case "TEACHER":
                Teacher teacher = new Teacher();
                teacher.setUsername(request.username());
                teacher.setFirstname(request.firstname());
                teacher.setLastname(request.lastname());
                teacher.setEmail(request.email());
                teacher.setDiscipline(request.discipline());
                School school = schoolRepository.findById(request.schoolId()).orElse(null);
                if (school != null) {
                    teacher.setSchool(school);
                    teacher.setPassword(passwordEncoder.encode(request.password()));
                    response = new RegisterResponse("Teacher Account created successfully");
                } else {
                    response = new RegisterResponse("Id of the school did not exist");
                }
                break;
            default:
                response = new RegisterResponse("Error, check the role you assign");
        }
        return response;
    }

    private boolean existsByEmail(String email) {
        return (studentService.isStudentExists(email) || teacherService.isTeacherExists(email) || adminService.isAdminExists(email));
    }

    private User findUserByUsernameOrEmail(String usernameOrEmail) {
        User user = adminService.getAdminByUsernameOrEmail(usernameOrEmail);
        if (user == null) {
            user = studentService.getStudentByUsernameOrEmail(usernameOrEmail);
        }
        if (user == null) {
            user = teacherService.getTeacherByUsernameOrEmail(usernameOrEmail);
        }
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        return user;
    }
}