package com.example.seedlist.repository;

import com.example.seedlist.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {

    List<Meeting> queryAllByProjectId(Integer projectId);

    List<Meeting> queryAllByStartTimeBetween(Date start, Date end);
}
