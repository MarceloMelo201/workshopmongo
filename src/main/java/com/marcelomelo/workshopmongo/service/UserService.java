package com.marcelomelo.workshopmongo.service;

import com.marcelomelo.workshopmongo.domain.entities.UserEntity;
import com.marcelomelo.workshopmongo.dtos.UserCreateDTO;
import com.marcelomelo.workshopmongo.repository.UserRepository;
import org.bson.types.ObjectId;
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


    public UserEntity findById(ObjectId idUSer){
        return userRepository.findById(idUSer).orElseThrow();
    }


    public UserEntity updateById(ObjectId id, UserCreateDTO dto){
        UserEntity user = userRepository.findById(id).orElseThrow();
        if(userRepository.existsByEmail(dto.email())) throw new RuntimeException();

        user.setName(dto.name());
        user.setEmail(dto.email());
        return userRepository.save(user);
    }


    public void deleteUser(ObjectId id){
        UserEntity user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
    }

}
