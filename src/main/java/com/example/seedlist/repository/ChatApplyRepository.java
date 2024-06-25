package com.example.seedlist.repository;

import com.example.seedlist.entity.BpApply;
import com.example.seedlist.entity.ChatApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ChatApplyRepository extends JpaRepository<ChatApply, Integer> {

    List<ChatApply> queryAllByProjectId(Integer projectId);
}
