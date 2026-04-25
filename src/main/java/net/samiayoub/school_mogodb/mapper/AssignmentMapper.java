package net.samiayoub.school_mogodb.mapper;

import net.samiayoub.school_mogodb.dto.requets.AssignmentRequest;
import net.samiayoub.school_mogodb.dto.responses.AssignmentResponse;
import net.samiayoub.school_mogodb.entity.Assignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {
    @Mapping(target = "id", ignore = true)
    Assignment toEntity(AssignmentRequest request);
    AssignmentResponse toDto(Assignment assignment);
    List<AssignmentResponse> toDtoList(List<Assignment> assignments);
}
