package com.example.seedlist.service;

import com.example.seedlist.entity.Financing;
import com.example.seedlist.repository.FinancingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FinancingService extends BaseService<FinancingRepository, Financing, Integer> {

    public FinancingService(FinancingRepository repository) {
        super(repository);
    }

    public List<Financing> getCompanyFinancing(Integer companyId) {
        return getRepository().findByCompanyId(companyId);
    }
}

