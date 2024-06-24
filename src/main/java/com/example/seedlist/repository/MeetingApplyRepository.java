package com.example.seedlist.repository;

import com.example.seedlist.entity.MeetingApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingApplyRepository extends JpaRepository<MeetingApply, Integer> {

    MeetingApply queryMeetingApplyByMeetingIdAndUid(Integer meetingId,Integer uid);

    List<MeetingApply> queryAllByMeetingId(Integer meetingId);

}
