package net.samiayoub.school_mogodb.service;

import net.samiayoub.school_mogodb.entity.User;
import net.samiayoub.school_mogodb.repository.AdminRepository;
import net.samiayoub.school_mogodb.repository.StudentRepository;
import net.samiayoub.school_mogodb.repository.TeacherRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;

    public CustomUserDetailsService(TeacherRepository teacherRepository, StudentRepository studentRepository, AdminRepository adminRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // 1. Is he an Admin ?
        User user = adminRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElse(null);

        // 2. Is he a Student ?
        if (user == null ) {
            user = studentRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElse(null);
        }

        // 3. Is he a Teacher ?
        if (user == null ) {
            user = teacherRepository
                    .findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + usernameOrEmail ));
        }

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
