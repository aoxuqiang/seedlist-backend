package com.example.seedlist.repository;

import com.example.seedlist.entity.BpSend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BpSendRepository extends JpaRepository<BpSend, Integer> {

    List<BpSend> queryAllByProjectId(Integer projectId);
}
