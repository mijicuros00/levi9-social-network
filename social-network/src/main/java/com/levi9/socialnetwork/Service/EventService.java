package com.levi9.socialnetwork.Service;

import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Event;
import com.levi9.socialnetwork.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class EventService {
    private static final String NOT_FOUND_MESSAGE = "Event not found for this id :: ";
    private static final String DUPLICATE_KEY_MESSAGE = "Event already exists with this id :: ";

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return this.eventRepository.findAll();
    }

    public Event getEventById(Long eventId) throws ResourceNotFoundException {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE + eventId));
    }

    public Event createEvent(Event event){
        return eventRepository.save(event);
    }

    public Event updateEvent(Long eventId, @RequestBody Event eventDetails) throws ResourceNotFoundException {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE + eventId));

        event.setLocationId(eventDetails.getLocationId());
        event.setStartDate(eventDetails.getStartDate());
        event.setEndDate(eventDetails.getEndDate());
        event.setUserId(eventDetails.getUserId());
        event.setGroupId(eventDetails.getGroupId());

        return eventRepository.save(event);
    }

    public void deleteEvent(Long eventId) throws ResourceNotFoundException {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE + eventId));

        eventRepository.delete(event);
    }
}
