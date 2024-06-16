package com.example.seedlist.service;

import com.example.seedlist.entity.Company;
import com.example.seedlist.repository.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyService extends BaseService<CompanyRepository, Company, Integer> {

    protected CompanyService(CompanyRepository repository) {
        super(repository);
    }

}
