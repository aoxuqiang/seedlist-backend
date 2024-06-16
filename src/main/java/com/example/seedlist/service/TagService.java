package com.example.seedlist.service;

import com.example.seedlist.entity.Tag;
import com.example.seedlist.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService extends BaseService<TagRepository,Tag,Integer> {

    public TagService(TagRepository repository) {
        super(repository);
    }

    public List<Tag> selectByIds(List<Integer> ids) {
        return getRepository().queryAllByIdIn(ids);
    }
}

