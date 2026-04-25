package net.samiayoub.school_mogodb.dto.responses;

public record TeacherResponse(
        String id,
        String username,
        String firstname,
        String lastname,
        String email,
        // String password,
        String discipline
        // SchoolResponse school,
        // List<CourseResponse> courses
) { }
