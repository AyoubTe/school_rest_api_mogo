package net.samiayoub.school_mogodb.controller;

import net.samiayoub.school_mogodb.dto.requets.AdminRequest;
import net.samiayoub.school_mogodb.dto.responses.AdminResponse;
import net.samiayoub.school_mogodb.mapper.AdminMapper;
import net.samiayoub.school_mogodb.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminController {
    private final AdminService adminService;
    private final AdminMapper adminMapper;

    public AdminController(AdminService adminService, AdminMapper adminMapper) {
        this.adminService = adminService;
        this.adminMapper = adminMapper;
    }

    @GetMapping
    public List<AdminResponse> getAdmins() {
        return adminService.getAllAdmins();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdminResponse createAdmin(@RequestBody AdminRequest adminRequest) {
        return adminService.registerAdmin(adminMapper.toEntity(adminRequest));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdminResponse updateAdmin(@RequestBody AdminRequest adminRequest) {
        return adminService.updateAdmin(adminMapper.toEntity(adminRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAdminById(@PathVariable String id) {
        adminService.deleteAdminById(id);
    }

    @GetMapping("/{id}")
    public AdminResponse getAdminById(@PathVariable String id) {
        return adminService.getAdminById(id);
    }
}
