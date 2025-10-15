package com.marcelomelo.workshopmongo.service;

import com.marcelomelo.workshopmongo.domain.entities.UserEntity;
import com.marcelomelo.workshopmongo.dtos.user.UserCreateDTO;
import com.marcelomelo.workshopmongo.dtos.user.UserResponseDTO;
import com.marcelomelo.workshopmongo.dtos.user.UserUpdateDTO;
import com.marcelomelo.workshopmongo.exception.EmailAlreadyExists;
import com.marcelomelo.workshopmongo.exception.UserNotFound;
import com.marcelomelo.workshopmongo.mapper.UserMapper;
import com.marcelomelo.workshopmongo.repository.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserService userService;

    @Nested
    class CreateUser {
        @Test
        @DisplayName("Should create user with success.")
        void shouldCreateUserSuccessfully() {
            UserCreateDTO dto = new UserCreateDTO("Marcelinho", "marcelo.teste@email.com");
            UserEntity user = getUserEntity(dto);
            UserResponseDTO response = getUserResponse(user);

            when(userMapper.toEntity(dto)).thenReturn(user);
            when(userRepository.save(user)).thenReturn(user);
            when(userMapper.toResponse(user)).thenReturn(response);

            UserResponseDTO result = userService.createUser(dto);

            assertAll("Verifying user fields.",
                    () -> assertEquals(user.getIdUser(), result.idUser()),
                    () -> assertEquals(user.getName(), result.name()),
                    () -> assertEquals(user.getEmail(), result.email()));

            verify(userRepository, times(1)).save(any(UserEntity.class));
        }

        @Test
        @DisplayName("Should throw exception for duplicate email.")
        void shouldThrowExceptionForDuplicateEmail() {
            UserCreateDTO dto = new UserCreateDTO("Marcelinho", "marcelo.teste2@email.com");

            when(userRepository.existsByEmail(dto.email())).thenReturn(true);

            EmailAlreadyExists exception = assertThrows(
                    EmailAlreadyExists.class,
                    () -> userService.createUser(dto)
            );

            assertEquals("Email already exists in database.", exception.getMessage());
            verify(userRepository, never()).save(any(UserEntity.class));
        }
    }

    @Nested
    class FindUserById {
        @Test
        @DisplayName("Should find user by id successfully.")
        void shouldFindUserByIdSuccessfully() {
            UserCreateDTO dto = new UserCreateDTO("Marcelinho", "marcelo.teste3@email.com");
            UserEntity user = getUserEntity(dto);
            UserResponseDTO response = getUserResponse(user);

            when(userRepository.findById(user.getIdUser())).thenReturn(Optional.of(user));
            when(userMapper.toResponse(user)).thenReturn(response);

            UserResponseDTO result = userService.findById(user.getIdUser());

            assertAll("Verifying user fields.",
                    () -> assertEquals(user.getIdUser(), result.idUser()),
                    () -> assertEquals(user.getName(), result.name()),
                    () -> assertEquals(user.getEmail(), result.email()));

            verify(userRepository, times(1)).findById(user.getIdUser());
        }

        @Test
        @DisplayName("Should throw exception for user not found.")
        void shouldThrowExceptionForUserNotFound() {
            UserCreateDTO dto = new UserCreateDTO("Marcelinho", "marcelo.teste3@email.com");
            UserEntity user = getUserEntity(dto);

            when(userRepository.findById(user.getIdUser())).thenReturn(Optional.empty());

            UserNotFound exception = assertThrows(
                    UserNotFound.class,
                    () -> userService.findById(user.getIdUser())
            );

            assertEquals("User not found.", exception.getMessage());
            verify(userRepository, times(1)).findById(user.getIdUser());
        }


    }

    @Nested
    class UpdateUser {

        @Test
        @DisplayName("Should update user by id.")
        void shouldUpdateUserById() {
            UserUpdateDTO update = new UserUpdateDTO("Marcelinho", "marcelo.teste6@email.com");

            UserEntity user = UserEntity
                    .builder()
                    .idUser(ObjectId.get())
                    .name(update.name())
                    .email(update.email())
                    .build();

            UserResponseDTO response = UserResponseDTO
                    .builder()
                    .idUser(user.getIdUser())
                    .name(update.name())
                    .email(update.email())
                    .build();

            when(userRepository.findById(any())).thenReturn(Optional.of(user));
            doReturn(response).when(userMapper).toResponse(any());

            UserResponseDTO result = userService.updateById(user.getIdUser(), update);

            assertAll("Verifying user fields.",
                    () -> assertEquals(user.getIdUser(), result.idUser()),
                    () -> assertEquals(user.getName(), result.name()),
                    () -> assertEquals(user.getEmail(), result.email()));

            verify(userRepository, times(1)).save(any(UserEntity.class));
        }

        @Test
        @DisplayName("Should throw exception for user not found.")
        void shouldThrowExceptionForUserNotFound() {
            UserUpdateDTO update = new UserUpdateDTO("Marcelinho", "marcelo.teste7@email.com");
            UserCreateDTO dto = new UserCreateDTO("Marcelo", "marcelo.teste5@email.com");
            UserEntity user = getUserEntity(dto);

            when(userRepository.findById(user.getIdUser())).thenReturn(Optional.empty());

            UserNotFound exception = assertThrows(
                    UserNotFound.class,
                    () -> userService.updateById(user.getIdUser(), update)
            );

            assertEquals("User not found.", exception.getMessage());
            verify(userRepository, times(1)).findById(user.getIdUser());
        }
    }

    @Nested
    class DeleteUser {
        @Test
        @DisplayName("should delete user successfully")
        void shouldDeleteUserSuccessfully() {
            ObjectId id = ObjectId.get();
            UserCreateDTO dto = new UserCreateDTO("Marcelo", "marcelo.teste5@email.com");
            UserEntity user = getUserEntity(dto);

            when(userRepository.findById(id)).thenReturn(Optional.of(user));

            userService.deleteUser(id);

            verify(userRepository, times(1)).findById(id);
            verify(userRepository, times(1)).delete(user);
        }

        @Test
        @DisplayName("should throw exception for user not found")
        void shouldThrowExceptionForUserNotFound() {
            ObjectId id = new ObjectId();

            when(userRepository.findById(id))
                    .thenReturn(Optional.empty());

            UserNotFound exception = assertThrows(
                    UserNotFound.class,
                    () -> userService.deleteUser(id));

            assertEquals("User not found.", exception.getMessage());
            verify(userRepository, never()).delete(any());
        }


    }

    private UserResponseDTO getUserResponse(UserEntity user) {
        return UserResponseDTO
                .builder()
                .idUser(user.getIdUser())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    private UserEntity getUserEntity(UserCreateDTO dto) {
        return UserEntity
                .builder()
                .idUser(new ObjectId())
                .name(dto.name())
                .email(dto.email())
                .build();
    }

}