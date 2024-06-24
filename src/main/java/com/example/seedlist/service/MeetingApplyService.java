package com.example.seedlist.service;

import com.example.seedlist.entity.MeetingApply;
import com.example.seedlist.repository.MeetingApplyRepository;
import org.springframework.stereotype.Service;


@Service
public class MeetingApplyService extends BaseService<MeetingApplyRepository, MeetingApply, Integer> {

    public MeetingApplyService(MeetingApplyRepository repository) {
        super(repository);
    }

    public MeetingApply selectUserMeeting(Integer meetingId, Integer uid) {
        return getRepository().queryMeetingApplyByMeetingIdAndUid(meetingId, uid);
    }
}
