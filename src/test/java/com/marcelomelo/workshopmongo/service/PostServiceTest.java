package com.marcelomelo.workshopmongo.service;

import com.marcelomelo.workshopmongo.domain.entities.PostEntity;
import com.marcelomelo.workshopmongo.domain.entities.UserEntity;
import com.marcelomelo.workshopmongo.dtos.post.PostCreateDTO;
import com.marcelomelo.workshopmongo.dtos.post.PostResponseDTO;
import com.marcelomelo.workshopmongo.dtos.post.PostUpdateDTO;
import com.marcelomelo.workshopmongo.exception.PostNotFound;
import com.marcelomelo.workshopmongo.exception.UserNotFound;
import com.marcelomelo.workshopmongo.mapper.PostMapper;
import com.marcelomelo.workshopmongo.repository.PostRepository;
import com.marcelomelo.workshopmongo.repository.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    PostMapper postMapper;

    @InjectMocks
    PostService postService;

    @Nested
    class CreatePost {

        @Test
        @DisplayName("Should create post with success.")
        void shouldCreatePostWithSuccess() {
            UserEntity user = getUser("Claudio", "claudiobrave@teste.com");
            PostCreateDTO dto = new PostCreateDTO(user.getIdUser(), "Morning vibes", "Starting the day with coffee and good energy.");
            PostEntity post = getPost(user, dto);
            PostResponseDTO response = getPostResponse(post);

            when(userRepository.findById(user.getIdUser())).thenReturn(Optional.of(user));
            when(postRepository.save(any(PostEntity.class))).thenReturn(post);
            when(postMapper.toResponse(post)).thenReturn(response);

            PostResponseDTO result = postService.createPost(dto);

            assertAll("Verifying post fields",
                    () -> assertEquals(response.idAuthor(), result.idAuthor()),
                    () -> assertEquals(response.date(), result.date()),
                    () -> assertEquals(response.title(), result.title()),
                    () -> assertEquals(response.body(), result.body())
            );

            verify(postRepository, times(1)).save(any(PostEntity.class));
        }

        @Test
        @DisplayName("Should throw exception for a user not found.")
        void shouldThrowExceptionForAUserNotFound() {
            ObjectId idUser = ObjectId.get();
            PostCreateDTO dto = new PostCreateDTO(idUser, "Morning vibes", "Starting the day with coffee and good energy.");

            when(userRepository.findById(idUser)).thenReturn(Optional.empty());

            UserNotFound exception = assertThrows(
                    UserNotFound.class,
                    () -> postService.createPost(dto)
            );
            assertEquals("User not found.", exception.getMessage());
            verify(userRepository, times(1)).findById(idUser);
        }
    }

    @Nested
    class FindById {

        @Test
        @DisplayName("Should find post by id.")
        void shouldFindPostById() {
            UserEntity user = getUser("Claudio", "claudiobrave@teste.com");
            PostCreateDTO dto = new PostCreateDTO(user.getIdUser(), "Morning vibes", "Starting the day with coffee and good energy.");
            PostEntity post = getPost(user, dto);

            ObjectId idPost = post.getIdPost();
            PostResponseDTO response = getPostResponse(post);

            when(postRepository.findById(idPost)).thenReturn(Optional.of(post));
            when(postMapper.toResponse(post)).thenReturn(response);

            PostResponseDTO result = postService.findById(idPost);

            assertAll("Verifying post fields",
                    () -> assertEquals(result.idAuthor(), response.idAuthor()),
                    () -> assertEquals(result.date(), response.date()),
                    () -> assertEquals(result.title(), response.title()),
                    () -> assertEquals(result.body(), response.body())
            );

            verify(postRepository, times(1)).findById(idPost);
        }

        @Test
        @DisplayName("Should throw exception for post not found.")
        void shouldThrowExceptionForPostNotFound() {
            ObjectId idPost = ObjectId.get();

            PostNotFound exception = assertThrows(
                    PostNotFound.class,
                    () -> postService.findById(idPost)
            );

            assertEquals("Post not found.", exception.getMessage());
            verify(postRepository, times(1)).findById(idPost);
        }
    }

    @Nested
    class UpdatePost {

        @Test
        @DisplayName("Should update post by id.")
        void shouldUpdatePostById() {
            UserEntity user = getUser("Mario", "marioegreyse@email.com");
            PostCreateDTO dto = new PostCreateDTO(user.getIdUser(), "Morning vibes", "Starting the day with coffee and good energy.");
            PostEntity post = getPost(user, dto);

            PostUpdateDTO dtoUpdate = new PostUpdateDTO("Update title", "Update text");

            PostEntity postUpdate = PostEntity
                    .builder()
                    .idPost(post.getIdPost())
                    .user(user)
                    .date(post.getDate())
                    .title(dtoUpdate.title())
                    .body(dtoUpdate.body())
                    .build();

            PostResponseDTO response = getPostResponse(postUpdate);

            when(postRepository.save(any(PostEntity.class))).thenReturn(postUpdate);
            when(postRepository.findById(post.getIdPost())).thenReturn(Optional.of(post));
            when(postMapper.toResponse(postUpdate)).thenReturn(response);

            PostResponseDTO result = postService.updatePost(post.getIdPost(), dtoUpdate);

            assertAll("Verifying post fields",
                    () -> assertEquals(result.idAuthor(), response.idAuthor()),
                    () -> assertEquals(result.date(), response.date()),
                    () -> assertEquals(result.title(), response.title()),
                    () -> assertEquals(result.body(), response.body())
            );

            verify(postRepository, times(1)).findById(post.getIdPost());
            verify(postRepository, times(1)).save(any(PostEntity.class));
        }
    }

    @Nested
    class DeletePost {

        @Test
        @DisplayName("Should delete post with success.")
        void shouldDeletePostWithSuccess() {
            UserEntity user = getUser("Claudio", "claudiobrave@teste.com");
            PostCreateDTO dto = new PostCreateDTO(user.getIdUser(), "Morning vibes", "Starting the day with coffee and good energy.");
            PostEntity post = getPost(user, dto);

            ObjectId idPost = post.getIdPost();

            when(postRepository.findById(idPost)).thenReturn(Optional.of(post));

            postService.deletePost(idPost);

            verify(postRepository, times(1)).findById(idPost);
            verify(postRepository, times(1)).delete(post);
        }
    }


    private PostResponseDTO getPostResponse(PostEntity post) {
        return PostResponseDTO
                .builder()
                .idAuthor(post.getUser() != null && post.getUser().getIdUser() != null
                        ? post.getUser().getIdUser().toHexString()
                        : null)
                .idPost(post.getIdPost() != null ? post.getIdPost().toHexString() : null)
                .date(post.getDate())
                .title(post.getTitle())
                .body(post.getBody())
                .comments(post.getComments())
                .build();
    }

    private PostEntity getPost(UserEntity user, PostCreateDTO dto) {
        return PostEntity
                .builder()
                .idPost(ObjectId.get())
                .user(user)
                .date(LocalDate.now())
                .title(dto.title())
                .body(dto.body())
                .build();
    }

    private UserEntity getUser(String name, String email) {
        return UserEntity
                .builder()
                .idUser(ObjectId.get())
                .name(name)
                .email(email)
                .build();
    }
}
