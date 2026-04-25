package net.samiayoub.school_mogodb.dto.requets;

public record StudentCourseDetailsRequest(
    Long studentId,
    Long courseId
) { }
