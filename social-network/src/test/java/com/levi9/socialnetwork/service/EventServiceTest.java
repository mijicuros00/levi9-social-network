package com.levi9.socialnetwork.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Event;
import com.levi9.socialnetwork.Repository.EventRepository;
import com.levi9.socialnetwork.Repository.UserRepository;
import com.levi9.socialnetwork.Service.EmailService;
import com.levi9.socialnetwork.Service.EventService;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class EventServiceTest {

    private static final String NOT_FOUND_MESSAGE = "Event not found for this id :: ";
    private static final String ALREADY_EXISTS_MESSAGE = "Event already exists with this id :: ";

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EmailService emailService;

    @InjectMocks
    private EventService eventService;

    @Test
    public void when_getEvents_it_should_return_ListOfEvents() {

        when(eventRepository.findAll()).thenReturn(List.of(new Event(), new Event()));

        assertThat(eventService.getAllEvents()).hasSize(2);
        verify(eventRepository, times(1)).findAll();
        verifyNoMoreInteractions();
    }

    @Test
    void should_find_and_return_one_Event() throws ResourceNotFoundException {
        
        Long eventId = 1L;
        Event expectedEvent = Event.builder().userId(1L).groupId(1l).id(1l).build();
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(expectedEvent));

        Event actual = eventService.getEventById(1L);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expectedEvent);
        verify(eventRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(eventRepository);
    }

    @Test
    void should_not_found_a_Event_that_doesnt_exists() {
        
        Event expectedEvent = Event.builder().userId(1L).groupId(1l).id(1l).build();
        Long eventId = 3L;
        when(eventRepository.findById(eventId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> eventService.getEventById(1L));
        verify(eventRepository, times(1)).findById(5L);
        verifyNoMoreInteractions(eventRepository);
    }

    @Test
    void should_delete_one_Event() throws ResourceNotFoundException {

        doNothing().when(eventRepository).deleteById(1L);

        eventService.deleteEvent(1L);
        verify(eventRepository, times(1)).deleteById(1L);
        verifyNoMoreInteractions(eventRepository);
    }

}
