package net.samiayoub.school_mogodb.mapper;

import net.samiayoub.school_mogodb.dto.requets.SchoolRequest;
import net.samiayoub.school_mogodb.dto.responses.SchoolResponse;
import net.samiayoub.school_mogodb.entity.School;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SchoolMapper {
    @Mapping(target = "id", ignore = true)
    School toEntity(SchoolRequest schoolRequest);
    SchoolResponse toDto(School school);
    List<SchoolResponse> toDtoList(List<School> schools);
}
