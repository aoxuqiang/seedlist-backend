package com.example.seedlist.service;

import com.example.seedlist.entity.Region;
import com.example.seedlist.repository.RegionRepository;
import org.springframework.stereotype.Service;

@Service
public class RegionService extends BaseService<RegionRepository, Region, Integer> {

    protected RegionService(RegionRepository repository) {
        super(repository);
    }

}
