package com.example.seedlist.repository;

import com.example.seedlist.entity.BpApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BpApplyRepository extends JpaRepository<BpApply, Integer> {

    List<BpApply> queryAllByProjectId(Integer projectId);
}
