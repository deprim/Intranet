package com.dandek.Intranet.Intranet.service;

import com.dandek.Intranet.Intranet.model.Events;
import com.dandek.Intranet.Intranet.repository.EventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class EventService {


    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional
    public Events save(Events event){
        return eventRepository.save(event);
    }

    public String getAllEventsInJSON() throws JsonProcessingException {
        List<Events> events = eventRepository.findAll();

        List<Map<String, String>> list = new ArrayList<>();

        for(Events event: events){
            Map<String, String> map = new HashMap<>();
            map.put("date", event.getDate().toString());
            map.put("title", event.getTitle());
            list.add(map);
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(list);

    }

    public Page<Events> getAllEventsPageable(int  page, int itemsPerPage) {

        Pageable pageable = PageRequest.of(page, itemsPerPage);
        return eventRepository.findAll(pageable);

    }

    @Transactional
    public void deleteEvent(Long id){
        eventRepository.deleteById(id);
    }




}
