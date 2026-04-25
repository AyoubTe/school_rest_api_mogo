package net.samiayoub.school_mogodb.security;

import net.samiayoub.school_mogodb.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private static final String ADMIN = "ADMIN";
    private static final String STUDENT = "STUDENT";
    private static final String TEACHER = "TEACHER";

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return  config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth ->
                                auth
                                        /* ##########################################
                                         *  Public endpoints
                                         *///########################################
                                        .requestMatchers("/api/v1/auth/**").permitAll()
                                        .requestMatchers(
                                                "/swagger-ui/**",
                                                "/swagger-ui.html",
                                                "/v3/api-docs/**",
                                                "/swagger-resources/**",
                                                "/webjars/**"
                                        ).permitAll()
                                        .requestMatchers("/actuator/health", "/actuator/info").permitAll()
                                        .requestMatchers("/files/**").permitAll()
                                        .requestMatchers("/ws/**").permitAll() // WebSockets

                                        /* ##########################################
                                         *  School endpoints
                                         *///########################################
                                        .requestMatchers(HttpMethod.GET, "/api/v1/schools", "/api/v1/schools/{id}").permitAll()
                                        .requestMatchers(HttpMethod.PUT, "/api/v1/schools/{id}").hasRole(ADMIN)
                                        .requestMatchers(HttpMethod.POST, "/api/v1/schools").hasRole(ADMIN)
                                        .requestMatchers(HttpMethod.DELETE, "/api/v1/schools/{id}").hasRole(ADMIN)

                                        /* ##########################################
                                         *  Assigment endpoints
                                         *///########################################
                                        .requestMatchers(HttpMethod.GET, "/api/v1/assignments", "/api/v1/assignments/{id}").permitAll()
                                        .requestMatchers(HttpMethod.PUT, "/api/v1/assignments/{id}").hasRole(TEACHER)
                                        .requestMatchers(HttpMethod.POST, "/api/v1/assignments").hasRole(TEACHER)
                                        .requestMatchers(HttpMethod.DELETE, "/api/v1/assignments/{id}").hasRole(TEACHER)

                                        /* ##########################################
                                         *  Course endpoints
                                         *///########################################
                                        .requestMatchers(HttpMethod.GET, "/api/v1/courses", "/api/v1/courses/{id}", "/api/v1/courses/{id}/teacher").permitAll()
                                        .requestMatchers(HttpMethod.PUT, "/api/v1/courses/{id}").hasRole(TEACHER)
                                        .requestMatchers(HttpMethod.POST, "/api/v1/courses").hasRole(TEACHER)
                                        .requestMatchers(HttpMethod.DELETE, "/api/v1/courses/{id}").hasRole(TEACHER)
                                        .requestMatchers(HttpMethod.GET, "/api/v1/courses/{id}/students").hasRole(TEACHER)

                                        /* ##########################################
                                         *  Admin endpoints
                                         *///########################################
                                        .requestMatchers("/api/v1/admins/**").hasRole(ADMIN)

                                        /* ##########################################
                                         *  Student endpoints
                                         *///########################################
                                        .requestMatchers(HttpMethod.GET, "/api/v1/students/myprofile", "/api/v1/students/mygrades", "/api/v1/students/mycourses").hasRole(STUDENT)
                                        .requestMatchers(HttpMethod.GET, "/api/v1/students").hasRole(ADMIN)
                                        .requestMatchers(HttpMethod.PUT, "/api/v1/students").hasRole(ADMIN)
                                        .requestMatchers(HttpMethod.DELETE, "/api/v1/students/{id}").hasRole(ADMIN)
                                        .requestMatchers(HttpMethod.POST, "/api/v1/students").hasRole(ADMIN)
                                        .requestMatchers(HttpMethod.GET, "/api/v1/students/{id}").hasAnyRole(ADMIN, TEACHER)
                                        .requestMatchers(HttpMethod.GET, "/api/v1/students/{id}/**").hasAnyRole(ADMIN, TEACHER)

                                        /* ##########################################
                                         *  Teacher endpoints
                                         *///########################################
                                        .requestMatchers(HttpMethod.GET, "/api/v1/teachers/myprofile", "/api/v1/teachers/mycourses", "/api/v1/teachers/myschool").hasRole(TEACHER)
                                        .requestMatchers(HttpMethod.GET, "/api/v1/teachers").hasRole(ADMIN)
                                        .requestMatchers(HttpMethod.POST, "/api/v1/teachers").hasRole(ADMIN)
                                        .requestMatchers(HttpMethod.PUT, "/api/v1/teachers").hasRole(ADMIN)
                                        .requestMatchers(HttpMethod.DELETE, "/api/v1/teachers/{id}").hasRole(ADMIN)
                                        .requestMatchers(HttpMethod.GET, "/api/v1/teachers/{id}").hasAnyRole(ADMIN, STUDENT)
                                        .requestMatchers("/api/v1/teachers").hasAnyRole(TEACHER, ADMIN)
                                        .requestMatchers(HttpMethod.GET, "/api/v1/teachers/{id}/**").hasRole(ADMIN)
                                        .anyRequest().authenticated()
                ).sessionManagement(
                        session ->
                                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }
}
