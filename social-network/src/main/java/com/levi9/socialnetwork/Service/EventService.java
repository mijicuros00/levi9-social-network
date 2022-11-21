package com.levi9.socialnetwork.Service;

import java.util.List;

import com.levi9.socialnetwork.Exception.ResourceExistsException;
import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Event;

public interface EventService {

	public List<Event> getAllEvents();

	public Event getEventById(Long eventId) throws ResourceNotFoundException;

	public Event createEvent(Event event) throws ResourceExistsException;

	public Event updateEvent(Long eventId, Event eventDetails) throws ResourceNotFoundException;

	public void deleteEvent(Long eventId) throws ResourceNotFoundException;
}
