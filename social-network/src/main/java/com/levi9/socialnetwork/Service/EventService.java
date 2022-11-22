package com.levi9.socialnetwork.Service;

import com.levi9.socialnetwork.Exception.ResourceExistsException;
import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Address;
import com.levi9.socialnetwork.Model.Event;
import com.levi9.socialnetwork.Model.Group;

import java.util.List;

public interface EventService {
    public List<Event> getAllEvents();
    public Event getEventById(Long eventId) throws ResourceNotFoundException;
    public Event createEvent(Event event) throws ResourceExistsException;
    public Event updateEvent(Long eventId, Event eventDetails) throws ResourceNotFoundException;
    public void deleteEvent(Long eventId) throws ResourceNotFoundException;
    Event createEventInGroup(Event event, Address address, Group group) throws ResourceExistsException;
    List<Event> getAllEventsInGroup(Long groupId);
}
