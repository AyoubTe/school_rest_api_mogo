package net.samiayoub.school_mogodb.mapper;

import net.samiayoub.school_mogodb.dto.requets.AssignmentDetailsRequest;
import net.samiayoub.school_mogodb.dto.responses.AssignmentDetailsResponse;
import net.samiayoub.school_mogodb.entity.AssignmentDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssignmentDetailsMapper {
    @Mapping(target = "id", ignore = true)
    AssignmentDetails toEntity(AssignmentDetailsRequest request);
    AssignmentDetailsResponse toDto(AssignmentDetails assignmentDetails);
    List<AssignmentDetailsResponse> toDtoList(List<AssignmentDetails> assignmentDetails);
}
