package net.samiayoub.school_mogodb.dto.responses;

public record SchoolResponse(
    String id,
    String name,
    String address
    // List<TeacherResponse> teachers
) { }
