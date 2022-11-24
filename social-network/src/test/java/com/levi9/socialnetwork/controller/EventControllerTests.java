package com.levi9.socialnetwork.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levi9.socialnetwork.Controller.EventController;
import com.levi9.socialnetwork.Model.Event;
import com.levi9.socialnetwork.Service.impl.EventServiceImpl;

import static org.mockito.BDDMockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebMvcTest(EventController.class)
class EventControllerTests {

    @MockBean
    private EventServiceImpl eventService;

    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void it_should_return_all_events() throws Exception {

        List<Event> listOfEvents = new ArrayList<>();
        listOfEvents.add(Event.builder().id(1L).userId(1l).groupId(1L).build());
        listOfEvents.add(Event.builder().id(2L).userId(1l).groupId(1L).build());
        given(eventService.getAllEvents()).willReturn(listOfEvents);

        ResultActions response = mockMvc.perform(get("/api/events"));

        response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.size()", is(listOfEvents.size())));
    }

    @Test
    public void givenEventId_whenGetEventById_thenReturnEventObject() throws Exception {

        long eventId = 1L;
        Event event = Event.builder().id(1L).userId(1l).groupId(1L).build();

        given(eventService.getEventById(eventId)).willReturn(event);

        ResultActions response = mockMvc.perform(get("/api/events/{id}", eventId));

        response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.id", is(event.getId())))
                .andExpect(jsonPath("$.idGroup", is(event.getGroupId())))
                .andExpect(jsonPath("$.idUser", is(event.getUserId())));

    }

    @Test
    public void givenEventId_whenGetEventById_thenReturnException() throws Exception {

        long eventId = 1L;
        Event event = Event.builder().id(1L).userId(1l).groupId(1L).build();

        given(eventService.getEventById(eventId)).willReturn(null);

        ResultActions response = mockMvc.perform(get("/api/events/{id}", eventId));

        response.andExpect(status().isNotFound()).andDo(print());

    }

    @Test
    public void givenUpdatedEvent_whenUpdateEvent_thenReturnUpdateEventObject() throws Exception {
    
        long eventId = 1L;
        Event savedEvent = Event.builder().id(1L).userId(1l).groupId(1L).build();

        Event updatedEvent = Event.builder().id(1L).userId(1l).groupId(2L).build();
        given(eventService.getEventById(eventId)).willReturn(savedEvent);
        given(eventService.updateEvent(eventId,updatedEvent))
                .willAnswer((invocation) -> invocation.getArgument(0));

        
        ResultActions response = mockMvc.perform(put("/api/events/{id}", eventId)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedEvent)));

        response.andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.id", is(updatedEvent.getId())))
                .andExpect(jsonPath("$.idGroup", is(updatedEvent.getGroupId())))
                .andExpect(jsonPath("$.idUser", is(updatedEvent.getUserId())));
    }


    @Test
    public void givenUpdatedEvent_whenUpdateEvent_thenReturn404() throws Exception {
  
        long eventId = 1L;
        Event savedEvent = Event.builder().id(1L).userId(1l).groupId(1L).build();

        Event updatedEvent = Event.builder().id(1L).userId(1l).groupId(2L).build();
        given(eventService.getEventById(eventId)).willReturn(null);
        given(eventService.updateEvent(eventId,updatedEvent))
                .willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(put("/api/events/{id}", eventId)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedEvent)));

        response.andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    public void givenEventId_whenDeleteEvent_thenReturn200() throws Exception {

        long eventId = 1L;
        willDoNothing().given(eventService).deleteEvent(eventId);

        ResultActions response = mockMvc.perform(delete("/api/events/{id}", eventId));
        response.andExpect(status().isOk()).andDo(print());
    }

}
