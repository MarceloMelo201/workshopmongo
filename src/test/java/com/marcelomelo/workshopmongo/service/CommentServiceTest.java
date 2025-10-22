package com.marcelomelo.workshopmongo.service;

import com.marcelomelo.workshopmongo.domain.entities.CommentEntity;
import com.marcelomelo.workshopmongo.domain.entities.PostEntity;
import com.marcelomelo.workshopmongo.domain.entities.UserEntity;
import com.marcelomelo.workshopmongo.dtos.comment.AuthorDTO;
import com.marcelomelo.workshopmongo.dtos.comment.CommentCreateDTO;
import com.marcelomelo.workshopmongo.dtos.comment.CommentResponseDTO;
import com.marcelomelo.workshopmongo.dtos.user.UserResponseDTO;
import com.marcelomelo.workshopmongo.exception.CommentNotFound;
import com.marcelomelo.workshopmongo.exception.PostNotFound;
import com.marcelomelo.workshopmongo.exception.UserNotFound;
import com.marcelomelo.workshopmongo.mapper.CommentMapper;
import com.marcelomelo.workshopmongo.repository.CommentRepository;
import com.marcelomelo.workshopmongo.repository.PostRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;

    @Mock
    CommentMapper commentMapper;

    @Mock
    PostRepository postRepository;

    @Mock
    UserService userService;

    @InjectMocks
    CommentService commentService;

    @Nested
    class CreateComment {

        @Test
        @DisplayName("Should create comment with success")
        void shouldCreateCommentWithSuccess() {

            ObjectId idPost = ObjectId.get();
            ObjectId idUser = ObjectId.get();

            UserEntity user = UserEntity.builder()
                    .idUser(idUser)
                    .name("Ferran")
                    .email("ferran.sorian@teste.com")
                    .build();

            UserResponseDTO userDto = UserResponseDTO.builder()
                    .idUser(user.getIdUser().toHexString())
                    .name(user.getName())
                    .email(user.getEmail())
                    .build();

            PostEntity post = getPost(idPost, user);

            CommentEntity comment = CommentEntity.builder()
                    .idPost(post.getIdPost())
                    .author(new AuthorDTO(user.getIdUser(), user.getName()))
                    .date(LocalDate.now())
                    .text("Text")
                    .build();

            post.getComments().add(comment);

            CommentResponseDTO response = CommentResponseDTO.builder()
                    .author(comment.getAuthor())
                    .date(comment.getDate())
                    .text(comment.getText())
                    .build();

            when(postRepository.findById(idPost)).thenReturn(Optional.of(post));
            when(userService.findById(idUser)).thenReturn(userDto);
            when(commentMapper.toResponse(any(CommentEntity.class))).thenReturn(response);

            CommentResponseDTO result = commentService.createComment(idUser, idPost, new CommentCreateDTO(comment.getText()));

            assertAll(
                    () -> assertEquals(response.author(), result.author()),
                    () -> assertEquals(response.date(), result.date()),
                    () -> assertEquals(response.text(), result.text())
            );

            verify(postRepository, times(1)).save(post);
        }

        @Test
        @DisplayName("Should throw exception for user not found")
        void shouldThrowExceptionForUserNotFound() {
            ObjectId idPost = ObjectId.get();
            ObjectId idUser = ObjectId.get();

            when(userService.findById(idUser)).thenThrow(UserNotFound.class);

            PostEntity post = getPost(idPost, new UserEntity());

            assertThrows(UserNotFound.class,
                    () -> commentService.createComment(idUser, idPost, new CommentCreateDTO("Text")));
        }

        @Test
        @DisplayName("Should throw exception for post not found")
        void shouldThrowExceptionForPostNotFound() {
            ObjectId idPost = ObjectId.get();
            ObjectId idUser = ObjectId.get();

            when(postRepository.findById(idPost)).thenReturn(Optional.empty());

            assertThrows(PostNotFound.class,
                    () -> commentService.createComment(idUser, idPost, new CommentCreateDTO("Text")));
        }
    }

    @Nested
    class FindAllComments {

        @Test
        @DisplayName("Should return all comments")
        void shouldReturnAllComments() {
            CommentEntity comment1 = CommentEntity.builder().text("Comment1").date(LocalDate.now()).build();
            CommentEntity comment2 = CommentEntity.builder().text("Comment2").date(LocalDate.now()).build();

            when(commentRepository.findAll()).thenReturn(List.of(comment1, comment2));
            when(commentMapper.toResponse(comment1)).thenReturn(CommentResponseDTO.builder().text("Comment1").date(comment1.getDate()).author(null).build());
            when(commentMapper.toResponse(comment2)).thenReturn(CommentResponseDTO.builder().text("Comment2").date(comment2.getDate()).author(null).build());

            List<CommentResponseDTO> result = commentService.findAll();

            assertEquals(2, result.size());
            assertEquals("Comment1", result.get(0).text());
            assertEquals("Comment2", result.get(1).text());
        }
    }


    @Nested
    class FindCommentById {

        @Test
        @DisplayName("Should find comment by id")
        void shouldFindCommentById() {

            ObjectId idComment = ObjectId.get();
            CommentEntity comment = CommentEntity.builder().idComment(idComment).text("Test").date(LocalDate.now()).build();

            when(commentRepository.findById(idComment)).thenReturn(Optional.of(comment));
            when(commentMapper.toResponse(comment)).thenReturn(CommentResponseDTO.builder().text("Test").date(comment.getDate()).author(null).build());

            CommentResponseDTO result = commentService.findById(idComment);

            assertEquals("Test", result.text());
        }

        @Test
        @DisplayName("Should throw exception when comment not found")
        void shouldThrowExceptionWhenCommentNotFound() {
            ObjectId idComment = ObjectId.get();
            when(commentRepository.findById(idComment)).thenReturn(Optional.empty());

            assertThrows(CommentNotFound.class, () -> commentService.findById(idComment));
        }
    }

    @Nested
    class UpdateComment {

        @Test
        @DisplayName("Should update comment with success")
        void shouldUpdateCommentWithSuccess() {
            ObjectId idPost = ObjectId.get();
            ObjectId idComment = ObjectId.get();

            PostEntity post = getPost(idPost, new UserEntity());
            CommentEntity comment = CommentEntity.builder().idComment(idComment).text("Old").date(LocalDate.now()).build();
            post.getComments().add(comment);

            when(postRepository.findById(idPost)).thenReturn(Optional.of(post));
            when(commentMapper.toResponse(comment)).thenReturn(CommentResponseDTO.builder().text("New").date(comment.getDate()).author(null).build());

            CommentResponseDTO result = commentService.updateComment(idComment, idPost, new CommentCreateDTO("New"));

            assertEquals("New", result.text());
            verify(postRepository, times(1)).save(post);
        }

        @Test
        @DisplayName("Should throw exception when post not found")
        void shouldThrowExceptionWhenPostNotFound() {
            ObjectId idPost = ObjectId.get();
            ObjectId idComment = ObjectId.get();

            when(postRepository.findById(idPost)).thenReturn(Optional.empty());

            assertThrows(PostNotFound.class, () -> commentService.updateComment(idComment, idPost, new CommentCreateDTO("New")));
        }

        @Test
        @DisplayName("Should throw exception when comment not found")
        void shouldThrowExceptionWhenCommentNotFound() {
            ObjectId idPost = ObjectId.get();
            ObjectId idComment = ObjectId.get();
            PostEntity post = getPost(idPost, new UserEntity());

            when(postRepository.findById(idPost)).thenReturn(Optional.of(post));

            assertThrows(CommentNotFound.class, () -> commentService.updateComment(idComment, idPost, new CommentCreateDTO("New")));
        }
    }

    @Nested
    class DeleteComment {

        @Test
        @DisplayName("Should delete comment with success")
        void shouldDeleteCommentWithSuccess() {
            ObjectId idPost = ObjectId.get();
            ObjectId idComment = ObjectId.get();

            PostEntity post = getPost(idPost, new UserEntity());
            CommentEntity comment = CommentEntity.builder().idComment(idComment).text("Delete").date(LocalDate.now()).build();
            post.getComments().add(comment);

            when(postRepository.findById(idPost)).thenReturn(Optional.of(post));

            commentService.deleteComment(idComment, idPost, new CommentCreateDTO("Delete"));

            assertTrue(post.getComments().isEmpty());
            verify(postRepository, times(1)).save(post);
        }

        @Test
        @DisplayName("Should throw exception when post not found")
        void shouldThrowExceptionWhenPostNotFound() {
            ObjectId idPost = ObjectId.get();
            ObjectId idComment = ObjectId.get();

            when(postRepository.findById(idPost)).thenReturn(Optional.empty());

            assertThrows(PostNotFound.class, () -> commentService.deleteComment(idComment, idPost, new CommentCreateDTO("Delete")));
        }

        @Test
        @DisplayName("Should throw exception when comment not found")
        void shouldThrowExceptionWhenCommentNotFound() {
            ObjectId idPost = ObjectId.get();
            ObjectId idComment = ObjectId.get();
            PostEntity post = getPost(idPost, new UserEntity());

            when(postRepository.findById(idPost)).thenReturn(Optional.of(post));

            assertThrows(CommentNotFound.class, () -> commentService.deleteComment(idComment, idPost, new CommentCreateDTO("Delete")));
        }
    }

    private PostEntity getPost(ObjectId id, UserEntity user) {
        return PostEntity.builder()
                .idPost(id)
                .user(user)
                .date(LocalDate.now())
                .title("Title")
                .body("Body")
                .comments(new ArrayList<>())
                .build();
    }
}
