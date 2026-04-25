package net.samiayoub.school_mogodb.dto.responses;

public record AssignmentDetailsResponse (
        String id,
        Boolean isDone,
        AssignmentResponse assignmentResponse
){ }
