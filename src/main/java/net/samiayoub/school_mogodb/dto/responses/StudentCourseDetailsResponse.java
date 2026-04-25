package net.samiayoub.school_mogodb.dto.responses;

public record StudentCourseDetailsResponse(
        String id,
        String studentId,
        String courseId
        // List<CourseResponse> courses,
        // GradeDetailsResponse grades
) { }
