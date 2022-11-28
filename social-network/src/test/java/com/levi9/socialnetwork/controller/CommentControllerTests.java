package com.levi9.socialnetwork.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levi9.socialnetwork.Controller.CommentController;
import com.levi9.socialnetwork.Exception.CustomExceptionHandler;
import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Comment;
import com.levi9.socialnetwork.Service.impl.CommentServiceImpl;
import com.levi9.socialnetwork.dto.ReplyDTO;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
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
		try(var ignored = MockitoAnnotations.openMocks(this)) {
			this.mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
		}
	}

	@Test
	void getAllComments() throws Exception {
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
		mockMvc.perform(get("/api/comments"))
				.andExpectAll(
						status().isOk(),
						jsonPath("$.size()", is(2)),
						jsonPath("$[0].id").value(1L),
						jsonPath("$[1].id").value(2L))
				.andDo(print());
	}

	@Test
	void getComment() throws Exception {
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
		mockMvc.perform(get("/api/comments/{id}", 1L))
				.andExpectAll(
						status().isOk(),
						jsonPath("$.id").value(1L))
				.andDo(print());

		given(commentService.getCommentById(2L))
				.willThrow(ResourceNotFoundException.class);
		mockMvc.perform(get("/api/comments/{id}", 2L))
				.andExpect(status().isNotFound())
				.andDo(print());
	}

	@Test
	void getCommentsByPost() throws Exception {
		List<Comment> commentList = new ArrayList<>();
		commentList.add(Comment.builder()
				.id(2L)
				.idUser(1L)
				.idPost(1L)
				.idRepliedTo(null)
				.text("Lorem")
				.createdDate(LocalDateTime.now())
				.deleted(false)
				.build());
		commentList.add(Comment.builder()
				.id(3L)
				.idUser(2L)
				.idPost(2L)
				.idRepliedTo(null)
				.text("ipsum")
				.createdDate(LocalDateTime.now())
				.deleted(false)
				.build());

		given(commentService.getCommentsByPost(1L))
				.willReturn(commentList.stream()
						.filter(c -> c.getIdPost() == 1L).toList());
		mockMvc.perform(get("/api/comments/post/{postId}", 1L))
				.andExpectAll(
						status().isOk(),
						jsonPath("$.size()", is(1)),
						jsonPath("$[0].id").value(2L),
						jsonPath("$[0].idPost").value(1L))
				.andDo(print());

		given(commentService.getCommentsByPost(3L))
				.willThrow(ResourceNotFoundException.class);
		mockMvc.perform(get("/api/comments/post/{postId}", 3L))
				.andExpect(status().isNotFound())
				.andDo(print());
	}

	@Test
	void getRepliesByComment() throws Exception {
		List<Comment> commentList = new ArrayList<>();
		commentList.add(Comment.builder()
				.id(1L)
				.idUser(1L)
				.idPost(1L)
				.idRepliedTo(null)
				.text("Lorem")
				.createdDate(LocalDateTime.now())
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

		given(commentService.getRepliesByComment(1L))
				.willReturn(commentList.stream()
						.filter(c -> c.getIdRepliedTo() != null && c.getIdRepliedTo() == 1L).toList());
		mockMvc.perform(get("/api/comments/{commentId}/reply", 1L))
				.andExpectAll(
						status().isOk(),
						jsonPath("$.size()", is(1)),
						jsonPath("$[0].id").value(2L),
						jsonPath("$[0].idRepliedTo").value(1L))
				.andDo(print());

		given(commentService.getRepliesByComment(2L))
				.willReturn(commentList.stream()
						.filter(c -> c.getIdRepliedTo() != null && c.getIdRepliedTo() == 2L).toList());
		mockMvc.perform(get("/api/comments/{commentId}/reply", 2L))
				.andExpectAll(
						status().isOk(),
						jsonPath("$.size()", is(0)))
				.andDo(print());

		given(commentService.getRepliesByComment(3L))
				.willThrow(ResourceNotFoundException.class);
		mockMvc.perform(get("/api/comments/{commentId}/reply", 3L))
				.andExpect(status().isNotFound())
				.andDo(print());
	}

//	@Test
//	void replyToComment() throws Exception {
//		ReplyDTO replyDTO = ReplyDTO.builder()
//				.idUser(1L)
//				.idPost(1L)
//				.idRepliedTo(1L)
//				.text("Lorem")
//				.createdDate(LocalDateTime.now())
//				.deleted(false)
//				.build();
//		Comment reply = Comment.builder()
//				.id(2L)
//				.idUser(1L)
//				.idPost(1L)
//				.idRepliedTo(1L)
//				.text("Lorem")
//				.createdDate(replyDTO.getCreatedDate())
//				.deleted(false)
//				.build();
//
//		given(commentService.replyToComment(replyDTO))
//				.willReturn(reply);
//		mockMvc.perform(post("/api/comments/reply", reply))
//				.andExpectAll(
//						status().isOk())
//				.andDo(print());
//	}
}