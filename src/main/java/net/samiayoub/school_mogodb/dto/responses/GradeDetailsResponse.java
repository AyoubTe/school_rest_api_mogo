package net.samiayoub.school_mogodb.dto.responses;

public record GradeDetailsResponse(
        String id,
        Integer gradeOne,
        Integer gradeTwo,
        Integer gradeThree,
        StudentCourseDetailsResponse studentCourseDetails
) { }
