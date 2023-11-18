package com.ecommerce.library.serviceImpliment;

import com.ecommerce.library.dto.AdminDto;
import com.ecommerce.library.model.Admin;
import com.ecommerce.library.repository.AdminRepository;
import com.ecommerce.library.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {
    private AdminRepository adminRepository;
    @Override
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    @Override
    public Admin save(AdminDto adminDto) {
        Admin admin=new Admin();
        admin.setId(adminDto.getId());
        admin.setName(adminDto.getName());
        admin.setEmail(adminDto.getEmail());
        admin.setPassword(adminDto.getPassword());
        admin.setUsername(adminDto.getUsername());
        admin.setRole("Admin");
        return adminRepository.save(admin);
    }
}
