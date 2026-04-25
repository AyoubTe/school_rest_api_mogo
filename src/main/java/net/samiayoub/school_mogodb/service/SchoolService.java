package net.samiayoub.school_mogodb.service;

import net.samiayoub.school_mogodb.dto.responses.SchoolResponse;
import net.samiayoub.school_mogodb.entity.School;
import net.samiayoub.school_mogodb.mapper.SchoolMapper;
import net.samiayoub.school_mogodb.repository.SchoolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private final SchoolMapper schoolMapper;

    public SchoolService(SchoolRepository schoolRepository, SchoolMapper schoolMapper) {
        this.schoolRepository = schoolRepository;
        this.schoolMapper = schoolMapper;
    }

    public SchoolResponse createSchool(School school) {
        return schoolMapper.toDto(schoolRepository.save(school));
    }

    public SchoolResponse updateSchool(School school) {
        return schoolMapper.toDto(schoolRepository.save(school));
    }

    public void deleteSchool(School school) {
        schoolRepository.delete(school);
    }

    public void deleteSchoolById(String id) {
        schoolRepository.deleteById(id);
    }

    public List<SchoolResponse> getSchools() {
        List<School> schools = schoolRepository.findAll();
        return schoolMapper.toDtoList(schools);
    }

    public SchoolResponse findSchoolById(String id) {
        return schoolMapper.toDto(schoolRepository.findById(id).orElse(null));
    }
}
