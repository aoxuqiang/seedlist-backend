package com.example.seedlist.service;

import com.example.seedlist.entity.Industry;
import com.example.seedlist.repository.IndustryRepository;
import org.springframework.stereotype.Service;


@Service
public class IndustryService extends BaseService<IndustryRepository, Industry, Integer> {

    protected IndustryService(IndustryRepository repository) {
        super(repository);
    }

}
