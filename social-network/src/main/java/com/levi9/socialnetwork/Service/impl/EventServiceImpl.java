package com.levi9.socialnetwork.Service.impl;

import com.levi9.socialnetwork.Exception.ResourceExistsException;
import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Event;
import com.levi9.socialnetwork.Repository.EventRepository;
import com.levi9.socialnetwork.Service.EventService;
import com.levi9.socialnetwork.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private static final String NOT_FOUND_MESSAGE = "Event not found for this id :: ";
    private static final String ALREADY_EXISTS_MESSAGE = "Event already exists with this id :: ";

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long eventId) throws ResourceNotFoundException {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE + eventId));
    }

    public Event createEvent(Event event) throws ResourceExistsException {
        Long eventId = event.getId();
        if (eventId != null && eventRepository.existsById(eventId)) {
            throw new ResourceExistsException(ALREADY_EXISTS_MESSAGE + eventId);
        }
        return eventRepository.save(event);
    }

    public Event updateEvent(Long eventId, Event eventDetails) throws ResourceNotFoundException {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE + eventId));

        event.setStartDate(eventDetails.getStartDate());
        event.setEndDate(eventDetails.getEndDate());

        return eventRepository.save(event);
    }

    public void deleteEvent(Long eventId) throws ResourceNotFoundException {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE + eventId));

        eventRepository.delete(event);
    }
}