package net.samiayoub.school_mogodb.mapper;

import net.samiayoub.school_mogodb.dto.requets.TeacherRequest;
import net.samiayoub.school_mogodb.dto.responses.TeacherResponse;
import net.samiayoub.school_mogodb.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    @Mapping(target = "id", ignore = true)
    Teacher toEntity(TeacherRequest teacherRequest);

    @Mapping(target = "courses.teacher", ignore = true)
    TeacherResponse toDto(Teacher teacher);
    List<TeacherResponse> toDtoList(List<Teacher> teacherList);
}
