package com.example.seedlist.service;

import com.example.seedlist.entity.Investor;
import com.example.seedlist.repository.InvestorRepository;
import org.springframework.stereotype.Service;

@Service
public class InvestorService extends BaseService<InvestorRepository, Investor,Integer> {

    protected InvestorService(InvestorRepository repository) {
        super(repository);
    }

    public Investor getByWx(String wxUserId) {
        return getRepository().getByWxUserId(wxUserId);
    }
}

