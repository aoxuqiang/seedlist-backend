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

    @Cacheable(value = "rootRegion",key = "#areaId")
    public Region getRoot(Integer areaId) {
        Region region = getById(areaId);
        while(region.getParentId() > 0) {
            region = getById(region.getParentId());
        }
        return region;
    }

}
