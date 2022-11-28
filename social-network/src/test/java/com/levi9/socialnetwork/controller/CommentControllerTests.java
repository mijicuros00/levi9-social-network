package com.levi9.socialnetwork.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levi9.socialnetwork.Controller.CommentController;
import com.levi9.socialnetwork.Exception.CustomExceptionHandler;
import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Comment;
import com.levi9.socialnetwork.Service.impl.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CommentController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@Import(CustomExceptionHandler.class)
class CommentControllerTests {

	@InjectMocks
	private CommentController commentController;

	@MockBean
	private CommentServiceImpl commentService;

	MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setup() throws Exception {
		try(var v = MockitoAnnotations.openMocks(this)) {
			this.mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
		}
	}

	@Test
	void testGetAllComments() throws Exception {
		List<Comment> commentList = new ArrayList<>();
		commentList.add(Comment.builder()
				.id(1L)
				.idUser(1L)
				.idPost(1L)
				.idRepliedTo(null)
				.text("Lorem")
				.createdDate(LocalDateTime.now().minus(Duration.ofHours(1)))
				.deleted(false)
				.build());
		commentList.add(Comment.builder()
				.id(2L)
				.idUser(2L)
				.idPost(1L)
				.idRepliedTo(1L)
				.text("ipsum")
				.createdDate(LocalDateTime.now())
				.deleted(false)
				.build());
		given(commentService.getAllComments())
				.willReturn(commentList);

		ResultActions response = mockMvc.perform(get("/api/comments"));

		response.andExpectAll(
						status().isOk(),
						jsonPath("$.size()", is(commentList.size())),
						jsonPath("$[0].id").value(1L),
						jsonPath("$[0].idUser").value(1L),
						jsonPath("$[0].idPost").value(1L),
						jsonPath("$[0].idRepliedTo", nullValue()),
						jsonPath("$[1].id").value(2L),
						jsonPath("$[1].idUser").value(2L),
						jsonPath("$[1].idPost").value(1L),
						jsonPath("$[1].idRepliedTo").value(1L))
				.andDo(print());
	}

	@Test
	void testGetCommentSuccessful() throws Exception {
		Comment comment = Comment.builder()
				.id(1L)
				.text("Lorem ipsum")
				.createdDate(LocalDateTime.now())
				.deleted(false)
				.idUser(1L)
				.idRepliedTo(null)
				.idPost(1L)
				.build();
		given(commentService.getCommentById(1L))
				.willReturn(comment);

		ResultActions response = mockMvc.perform(get("/api/comments/{id}", 1L));
		response.andExpectAll(
						status().isOk(),
						jsonPath("$.id").value(1L))
				.andDo(print());
	}

	@Test
	void testGetCommentUnsuccessful() throws Exception {
		given(commentService.getCommentById(1L))
				.willThrow(ResourceNotFoundException.class);

		ResultActions response = mockMvc.perform(get("/api/comments/{id}", 1L));

		response.andExpect(status().isNotFound())
				.andDo(print());
	}

}
