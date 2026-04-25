package net.samiayoub.school_mogodb.mapper;

import net.samiayoub.school_mogodb.dto.requets.StudentCourseDetailsRequest;
import net.samiayoub.school_mogodb.dto.responses.StudentCourseDetailsResponse;
import net.samiayoub.school_mogodb.entity.StudentCourseDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentCourseDetailsMapper {
    @Mapping(target = "id", ignore = true)
    StudentCourseDetails toEntity(StudentCourseDetailsRequest studentCourseDetailsRequest);
    StudentCourseDetailsResponse toDto(StudentCourseDetails studentCourseDetails);
    List<StudentCourseDetailsResponse> toDtoList(List<StudentCourseDetails> studentCourseDetails);
}
