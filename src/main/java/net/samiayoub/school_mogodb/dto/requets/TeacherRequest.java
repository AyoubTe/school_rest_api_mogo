package net.samiayoub.school_mogodb.dto.requets;

public record TeacherRequest(
        String username,
        String firstname,
        String lastname,
        String email,
        String password,
        String discipline
) { }
