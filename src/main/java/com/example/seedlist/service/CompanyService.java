package com.example.seedlist.service;

import com.example.seedlist.entity.Company;
import com.example.seedlist.enums.CompanyType;
import com.example.seedlist.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CompanyService extends BaseService<CompanyRepository, Company, Integer> {

    protected CompanyService(CompanyRepository repository) {
        super(repository);
    }

    public List<Company> findByType(CompanyType companyType) {
        List<Company> result = getRepository().findByType(companyType.getType());
        Collections.reverse(result);
        return result;
    }

}
