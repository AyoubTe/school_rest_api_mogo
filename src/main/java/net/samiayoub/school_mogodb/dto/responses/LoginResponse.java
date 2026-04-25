package net.samiayoub.school_mogodb.dto.responses;


public record LoginResponse (
        String token,
        String message,
        String role
) { }
