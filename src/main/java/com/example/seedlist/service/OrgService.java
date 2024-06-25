package com.example.seedlist.service;

import com.example.seedlist.entity.Org;
import com.example.seedlist.repository.OrgRepository;
import org.springframework.stereotype.Service;


@Service
public class OrgService extends BaseService<OrgRepository, Org,Integer> {

    protected OrgService(OrgRepository repository) {
        super(repository);
    }
}

