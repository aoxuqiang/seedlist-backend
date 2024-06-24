package com.example.seedlist.service;

import com.example.seedlist.entity.BpSend;
import com.example.seedlist.repository.BpSendRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BpSendService extends BaseService<BpSendRepository, BpSend, Integer> {

    protected BpSendService(BpSendRepository repository) {
        super(repository);
    }

    public List<BpSend> queryByProject(Integer projectId) {
        return getRepository().queryAllByProjectId(projectId);
    }
}
