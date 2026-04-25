package net.samiayoub.school_mogodb.mapper;

import net.samiayoub.school_mogodb.dto.requets.AdminRequest;
import net.samiayoub.school_mogodb.dto.responses.AdminResponse;
import net.samiayoub.school_mogodb.entity.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    @Mapping(target = "id", ignore = true)
    Admin toEntity(AdminRequest adminRequest);
    AdminResponse toDto(Admin admin);
    List<AdminResponse> toDtoList(List<Admin> adminList);
}
