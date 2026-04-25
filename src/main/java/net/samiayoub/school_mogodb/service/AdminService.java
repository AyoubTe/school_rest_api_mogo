package net.samiayoub.school_mogodb.service;

import net.samiayoub.school_mogodb.dto.responses.AdminResponse;
import net.samiayoub.school_mogodb.entity.Admin;
import net.samiayoub.school_mogodb.exception.ResourceNotFoundException;
import net.samiayoub.school_mogodb.mapper.AdminMapper;
import net.samiayoub.school_mogodb.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;

    public AdminService(AdminRepository adminRepository, AdminMapper adminMapper) {
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
    }

    public AdminResponse registerAdmin(Admin admin) {
        return adminMapper.toDto(adminRepository.save(admin));
    }

    public AdminResponse updateAdmin(Admin admin) {
        return adminMapper.toDto(adminRepository.save(admin));
    }

    public void deleteAdmin(Admin admin) {
        adminRepository.delete(admin);
    }

    public void deleteAdminById(String id) {
        adminRepository.deleteById(id);
    }

    public List<AdminResponse> getAllAdmins() {
        return adminMapper.toDtoList(adminRepository.findAll());
    }

    public AdminResponse getAdminById(String id) {
        Admin admin = adminRepository.findById(id).orElse(null);
        if(admin == null){
            throw  new ResourceNotFoundException("Admin with id " + id + " not found");
        }
        return adminMapper.toDto(admin);
    }

    public boolean isAdminExists(String emailOrUsername) {
        return (adminRepository.findByUsername(emailOrUsername).orElse(null) != null) || (adminRepository.findByEmail(emailOrUsername).orElse(null) != null);
    }

    public Admin getAdminByUsernameOrEmail(String usernameOrEmail) {
        return adminRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElse(null);
    }
}
