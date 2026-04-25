package net.samiayoub.school_mogodb.dto.requets;

import java.util.Date;
import java.util.List;

public record AssignmentRequest(
        String name,
        String description,
        Date dueDate,
        List<AssignmentDetailsRequest> details
) {
}
