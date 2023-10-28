package com.example.seedlist.service;

import com.example.seedlist.entity.Tag;
import com.example.seedlist.repository.TagRepository;
import org.springframework.stereotype.Service;

@Service
public class TagService extends BaseService<TagRepository,Tag,Integer> {

    protected TagService(TagRepository repository) {
        super(repository);
    }
}

