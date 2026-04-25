package net.samiayoub.school_mogodb.dto.requets;

public record AdminRequest(
        String username,
        String firstname,
        String lastname,
        String email,
        String password,
        String mission
) { }
