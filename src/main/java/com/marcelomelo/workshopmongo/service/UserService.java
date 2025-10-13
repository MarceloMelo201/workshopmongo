package com.marcelomelo.workshopmongo.service;

import com.marcelomelo.workshopmongo.domain.entities.UserEntity;
import com.marcelomelo.workshopmongo.dtos.UserCreateDTO;
import com.marcelomelo.workshopmongo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity createUser(UserCreateDTO dto){
        UserEntity user = UserEntity
                .builder()
                .name(dto.name())
                .email(dto.email())
                .build();

        return userRepository.save(user);
    }

    public List<UserEntity> findAll(){
        return userRepository.findAll();
    }


}
