package com.example.seedlist.service;

import cn.hutool.core.date.DateUtil;
import com.example.seedlist.entity.Meeting;
import com.example.seedlist.entity.MeetingApply;
import com.example.seedlist.entity.MeetingInvite;
import com.example.seedlist.repository.MeetingApplyRepository;
import com.example.seedlist.repository.MeetingInviteRepository;
import com.example.seedlist.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class MeetingService extends BaseService<MeetingRepository, Meeting, Integer> {

    @Autowired
    private MeetingApplyRepository meetingApplyRepository;

    @Autowired
    private MeetingInviteRepository meetingInviteRepository;

    public MeetingService(MeetingRepository repository) {
        super(repository);
    }

    public List<Meeting> selectByProject(Integer projectId) {
        return getRepository().queryAllByProjectId(projectId);
    }

    public List<MeetingInvite> queryMeetingInviteList(Integer meetingId) {
        return meetingInviteRepository.queryAllByMeetingId(meetingId);
    }

    public List<MeetingApply> queryMeetingApplyList(Integer meetingId) {
        return meetingApplyRepository.queryAllByMeetingId(meetingId);
    }

    public List<Meeting> queryMonthMeetings(Date date) {
        return getRepository().queryAllByStartTimeBetween(DateUtil.beginOfMonth(date), DateUtil.endOfMonth(date));
    }

    public void saveMeetingInviteList(List<MeetingInvite> meetingInviteList) {
        meetingInviteRepository.saveAll(meetingInviteList);
    }
}
