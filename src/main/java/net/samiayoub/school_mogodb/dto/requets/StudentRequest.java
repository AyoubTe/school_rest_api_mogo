package net.samiayoub.school_mogodb.dto.requets;

public record StudentRequest(
        String username,
        String firstname,
        String lastname,
        String email,
        String password
) {
}
