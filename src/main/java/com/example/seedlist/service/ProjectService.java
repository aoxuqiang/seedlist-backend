package com.example.seedlist.service;

import com.example.seedlist.entity.Project;
import com.example.seedlist.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ProjectService extends BaseService<ProjectRepository, Project, Integer> {

    public ProjectService(ProjectRepository repository) {
        super(repository);
    }
}

