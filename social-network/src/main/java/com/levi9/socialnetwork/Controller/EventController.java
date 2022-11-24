package com.levi9.socialnetwork.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi9.socialnetwork.Exception.ResourceExistsException;
import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Event;
import com.levi9.socialnetwork.Service.EventService;
import com.levi9.socialnetwork.Service.EventService;

import com.levi9.socialnetwork.Service.impl.EventServiceImpl;

import com.levi9.socialnetwork.dto.EventDTO;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventServiceImpl eventService;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {

        List<Event> allEvents = eventService.getAllEvents();
        return new ResponseEntity<>(allEvents, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable(value = "id") Long eventId) throws ResourceNotFoundException {

        Event event = eventService.getEventById(eventId);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody EventDTO eventDTO) throws ResourceExistsException {

        Event event = eventService.createEvent(new Event(eventDTO));
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable(value = "id") Long eventId, @RequestBody EventDTO eventDTO)
            throws ResourceNotFoundException {

        Event event = eventService.updateEvent(eventId, new Event(eventDTO));
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @DeleteMapping("/expired")
    public ResponseEntity<List<Event>> deleteAllExpiredEvents() throws ResourceNotFoundException {

        return new ResponseEntity<>(eventService.deleteAllExpiredEvents(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteEvent(@PathVariable(value = "id") Long eventId)
            throws ResourceNotFoundException {

        eventService.deleteEvent(eventId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
