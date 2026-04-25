package net.samiayoub.school_mogodb.mapper;

import net.samiayoub.school_mogodb.dto.requets.CourseRequest;
import net.samiayoub.school_mogodb.dto.responses.CourseResponse;
import net.samiayoub.school_mogodb.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(target = "id", ignore = true)
    Course toEntity(CourseRequest courseRequest);
    CourseResponse toDto(Course course);
    List<CourseResponse> toDtoList(List<Course> courses);
}
