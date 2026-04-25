package net.samiayoub.school_mogodb.mapper;

import net.samiayoub.school_mogodb.dto.requets.StudentRequest;
import net.samiayoub.school_mogodb.dto.responses.StudentResponse;
import net.samiayoub.school_mogodb.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(target = "id", ignore = true)
    Student toEntity(StudentRequest studentRequest);
    StudentResponse toDto(Student student);
    List<StudentResponse> toDtoList(List<Student> studentList);
}
