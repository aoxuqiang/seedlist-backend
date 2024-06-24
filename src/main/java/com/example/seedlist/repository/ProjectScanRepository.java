package com.example.seedlist.repository;

import com.example.seedlist.entity.ProjectScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectScanRepository extends JpaRepository<ProjectScan, Integer> {
    List<ProjectScan> queryAllByProjectId(Integer projectId);
}
