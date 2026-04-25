package net.samiayoub.school_mogodb.dto.requets;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record RegisterRequest (
        @NotBlank(message = "Username is mandatory")
        @Size(min = 6, max = 50, message = "The username size must be between 6 and 50 characters")
        String username,
        @NotBlank(message = "First name is mandatory")
        String firstname,
        @NotBlank(message = "Last name is mandatory")
        String lastname,
        @NotBlank(message = "Password id mandatory")
        @Size(min = 8)
        String password,
        @Email(message = "That must be an email")
        String email,
        String mission,
        String discipline,
        String schoolId,
        @NotNull(message = "The role is mandatory to create the appropriate account")
        String role
) { }
