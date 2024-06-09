package com.example.seedlist.service;

import com.example.seedlist.entity.Event;
import com.example.seedlist.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService extends BaseService<EventRepository, Event, Integer> {

    protected EventService(EventRepository repository) {
        super(repository);
    }


    public List<Event> queryUserEvent(String userId, Integer eventId) {
        return getRepository().queryAllByUseridAndEvent(userId, eventId);
    }
}
