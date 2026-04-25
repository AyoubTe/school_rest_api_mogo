package net.samiayoub.school_mogodb.dto.requets;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "The email or password is mandatory")
        String emailOrUsername,
        @NotBlank(message = "Password is mandatory to let you in")
        @Size(min = 8)
        String password
) { }
