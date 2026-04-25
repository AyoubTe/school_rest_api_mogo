package net.samiayoub.school_mogodb.dto.responses;

public record CourseResponse(
        String id,
        String code,
        String name
        // TeacherResponse teacher,
        // List<StudentResponse> students
) { }
