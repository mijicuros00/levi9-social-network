package com.levi9.socialnetwork.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Event;
import com.levi9.socialnetwork.Repository.EventRepository;
import com.levi9.socialnetwork.Repository.UserRepository;
import com.levi9.socialnetwork.Service.impl.EmailServiceImpl;
import com.levi9.socialnetwork.Service.impl.EventServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTests {

	private static final String NOT_FOUND_MESSAGE = "Event not found for this id :: ";
	private static final String ALREADY_EXISTS_MESSAGE = "Event already exists with this id :: ";

	@Mock
	private EventRepository eventRepository;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private EmailServiceImpl emailService;

	@InjectMocks
	private EventServiceImpl eventService;

	static final Long eventId = 1L;

	@Test
	public void whenGetEventsItShouldReturnListOfEvents() {

		given(eventRepository.findAll()).willReturn(List.of(new Event(), new Event()));

		assertThat(eventService.getAllEvents()).hasSize(2);
		verify(eventRepository, times(1)).findAll();
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	void shouldFindAndReturnOneEvent() throws ResourceNotFoundException {

		Event expectedEvent = Event.builder().userId(1L).groupId(1l).id(1l).build();
		when(eventRepository.findById(eventId)).thenReturn(Optional.of(expectedEvent));

		Event actual = eventService.getEventById(1L);

		assertThat(actual).usingRecursiveComparison().isEqualTo(expectedEvent);
		verify(eventRepository, times(1)).findById(1L);
		verifyNoMoreInteractions(eventRepository);

	}

	@Test()
	void shouldNotFindEventAndReturnException() throws ResourceNotFoundException {

		given(eventRepository.findById(eventId)).willAnswer(invocation -> {
			throw new ResourceNotFoundException("Event not found");
		});

		assertThrows(ResourceNotFoundException.class, () -> {

			eventService.getEventById(eventId);

		});
	}

	@Test
	void shouldNotFoundAEventThatDoesntExist() {

		Event expectedEvent = Event.builder().userId(1L).groupId(1l).id(1l).build();
		given(eventRepository.findById(eventId)).willAnswer(invocation -> {
			throw new ResourceNotFoundException("Event not found");
		});

		assertThrows(ResourceNotFoundException.class, () -> eventService.getEventById(3L));
		verify(eventRepository, times(1)).findById(3L);
		verifyNoMoreInteractions(eventRepository);
	}

	@Test
	void shouldDeleteOneEvent() throws ResourceNotFoundException {

		Event expectedEvent = Event.builder().userId(1L).groupId(1l).id(1l).build();
		when(eventRepository.save(expectedEvent)).thenReturn(expectedEvent);

		eventRepository.deleteById(eventId);

		verify(eventRepository, times(1)).deleteById(eventId);
		verifyNoMoreInteractions(eventRepository);
	}

	@Test
	void shouldDeleteOneEventReturnException() throws ResourceNotFoundException {

		Event expectedEvent = Event.builder().userId(1L).groupId(1l).id(1l).build();
		when(eventRepository.save(expectedEvent)).thenReturn(expectedEvent);

		eventRepository.deleteById(2L);

		assertThrows(ResourceNotFoundException.class, () -> {

			eventService.deleteEvent(2l);

		});

	}

}
