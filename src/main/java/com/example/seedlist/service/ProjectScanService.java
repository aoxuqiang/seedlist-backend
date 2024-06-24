package com.example.seedlist.service;

import com.example.seedlist.entity.ProjectScan;
import com.example.seedlist.repository.ProjectScanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectScanService extends BaseService<ProjectScanRepository, ProjectScan, Integer> {

    protected ProjectScanService(ProjectScanRepository repository) {
        super(repository);
    }

    public List<ProjectScan> queryByProject(Integer projectId) {
        return getRepository().queryAllByProjectId(projectId);
    }
}
