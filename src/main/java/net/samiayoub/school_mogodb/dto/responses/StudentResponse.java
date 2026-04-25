package net.samiayoub.school_mogodb.dto.responses;

public record StudentResponse(
        String id,
        String username,
        String firstname,
        String lastname,
        String email
        // String password
) { }
