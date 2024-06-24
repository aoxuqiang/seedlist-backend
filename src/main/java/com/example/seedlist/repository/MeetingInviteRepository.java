package com.example.seedlist.repository;

import com.example.seedlist.entity.MeetingInvite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingInviteRepository extends JpaRepository<MeetingInvite, Integer> {

    MeetingInvite queryMeetingApplyByMeetingIdAndUid(Integer meetingId,Integer uid);

    List<MeetingInvite> queryAllByMeetingId(Integer meetingId);
}
