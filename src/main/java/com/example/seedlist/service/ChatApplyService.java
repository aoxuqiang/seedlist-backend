package com.example.seedlist.service;

import com.example.seedlist.entity.BpApply;
import com.example.seedlist.entity.ChatApply;
import com.example.seedlist.repository.BpApplyRepository;
import com.example.seedlist.repository.ChatApplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ChatApplyService extends BaseService<ChatApplyRepository, ChatApply, Integer> {

    protected ChatApplyService(ChatApplyRepository repository) {
        super(repository);
    }

    public List<ChatApply> queryByProject(Integer projectId) {
        return getRepository().queryAllByProjectId(projectId);
    }
}
