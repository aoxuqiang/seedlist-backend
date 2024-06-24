package com.example.seedlist.service;

import com.example.seedlist.entity.BpApply;
import com.example.seedlist.repository.BpApplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BpApplyService extends BaseService<BpApplyRepository, BpApply, Integer> {

    protected BpApplyService(BpApplyRepository repository) {
        super(repository);
    }

    public List<BpApply> queryByProject(Integer projectId) {
        return getRepository().queryAllByProjectId(projectId);
    }
}
