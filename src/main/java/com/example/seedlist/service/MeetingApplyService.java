package com.example.seedlist.service;

import com.example.seedlist.entity.MeetingApply;
import com.example.seedlist.repository.MeetingApplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MeetingApplyService extends BaseService<MeetingApplyRepository, MeetingApply, Integer> {

    public MeetingApplyService(MeetingApplyRepository repository) {
        super(repository);
    }

    public MeetingApply selectUserMeeting(Integer meetingId, Integer uid) {
        return getRepository().queryMeetingApplyByMeetingIdAndUid(meetingId, uid);
    }

    public List<MeetingApply> selectByUser(Integer uid) {
        return getRepository().queryAllByUidAndAuditStatus(uid,1);
    }
}
