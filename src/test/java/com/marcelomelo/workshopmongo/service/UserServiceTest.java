package com.marcelomelo.workshopmongo.service;

import com.marcelomelo.workshopmongo.mapper.UserMapper;
import com.marcelomelo.workshopmongo.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserService userService;

    @Nested
    class CreateUser{

        @Test
        @DisplayName("Should create user with success.")
        void shouldCreateUserSuccessfully(){

        }
    }

}