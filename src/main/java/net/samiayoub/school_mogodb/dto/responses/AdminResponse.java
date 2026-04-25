package net.samiayoub.school_mogodb.dto.responses;

public record AdminResponse (
        String id,
        String username,
        String firstname,
        String lastname,
        String email,
        // String password,
        String mission
) {
}
