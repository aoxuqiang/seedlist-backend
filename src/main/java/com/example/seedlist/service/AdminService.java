package com.example.seedlist.service;

import com.example.seedlist.entity.Admin;
import com.example.seedlist.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService extends BaseService<AdminRepository, Admin, Integer> {

    public AdminService(AdminRepository repository) {
        super(repository);
    }

    public Optional<Admin> getAdminByAccount(String account) {
        return getRepository().findByAccount(account);
    }

}
