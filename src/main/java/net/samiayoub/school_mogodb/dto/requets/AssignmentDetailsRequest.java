package net.samiayoub.school_mogodb.dto.requets;

import net.samiayoub.school_mogodb.dto.responses.AssignmentResponse;

public record AssignmentDetailsRequest (
        Boolean isDone,
        AssignmentResponse assignmentResponse
) { }
