package net.samiayoub.school_mogodb.controller;

import jakarta.validation.Valid;
import net.samiayoub.school_mogodb.dto.requets.LoginRequest;
import net.samiayoub.school_mogodb.dto.requets.RegisterRequest;
import net.samiayoub.school_mogodb.dto.responses.RegisterResponse;
import net.samiayoub.school_mogodb.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            // Si le login réussit
            return new ResponseEntity<>(authService.login(request), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            // Si le login échoue (mot de passe ou username faux), on évite l'erreur 500
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiant ou mot de passe incorrect");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent().build();
    }
}