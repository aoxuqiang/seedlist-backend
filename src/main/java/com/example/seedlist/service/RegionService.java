package com.example.seedlist.service;

import com.example.seedlist.entity.Region;
import com.example.seedlist.repository.RegionRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService extends BaseService<RegionRepository, Region, Integer> {

    protected RegionService(RegionRepository repository) {
        super(repository);
    }

    @Cacheable("allRegion")
    public List<Region> getAllRegsion() {
        return getAll();
    }

}
