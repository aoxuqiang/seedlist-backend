package com.example.seedlist.repository;

import com.example.seedlist.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    List<Event> queryAllByUseridAndEvent(String userId,Integer event);
}
