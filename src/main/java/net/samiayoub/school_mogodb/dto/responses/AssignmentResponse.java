package net.samiayoub.school_mogodb.dto.responses;

import java.util.Date;
import java.util.List;

public record AssignmentResponse(
    String id,
    String name,
    String description,
    Date dueDate,
    List<AssignmentDetailsResponse> assignmentDetailsResponse
) { }
