package com.levi9.socialnetwork.service;

import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Comment;
import com.levi9.socialnetwork.Repository.CommentRepository;
import com.levi9.socialnetwork.Service.impl.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTests {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    void setup() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllComments() throws ResourceNotFoundException {
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

        given(commentRepository.findAll())
                .willReturn(commentList);

        List<Comment> returnedComments = commentService.getAllComments();
        assertThat(returnedComments).hasSize(2);
        assertThat(returnedComments.get(0).getId()).isEqualTo(1L);
        assertThat(returnedComments.get(1).getId()).isEqualTo(2L);
        verify(commentRepository, times(1)).findAll();
    }
}
