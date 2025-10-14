package com.marcelomelo.workshopmongo.service;

import com.marcelomelo.workshopmongo.domain.entities.UserEntity;
import com.marcelomelo.workshopmongo.dtos.UserCreateDTO;
import com.marcelomelo.workshopmongo.dtos.UserResponseDTO;
import com.marcelomelo.workshopmongo.exception.UserNotFound;
import com.marcelomelo.workshopmongo.mapper.UserMapper;
import com.marcelomelo.workshopmongo.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserResponseDTO createUser(UserCreateDTO dto){
        if(userRepository.existsByEmail(dto.email())) throw new UserNotFound();
        UserEntity user = userMapper.toEntity(dto);
        return userMapper.toResponse(userRepository.save(user));
    }

    public List<UserEntity> findAll(){
        return userRepository.findAll();
    }

    public UserResponseDTO findById(ObjectId idUSer){
        return userMapper.toResponse(userRepository.findById(idUSer).orElseThrow(UserNotFound::new));
    }

    public UserResponseDTO updateById(ObjectId id, UserCreateDTO dto){
        UserEntity user = userRepository.findById(id).orElseThrow();
        if(userRepository.existsByEmail(dto.email())) throw new UserNotFound();

        user.setName(dto.name());
        user.setEmail(dto.email());
        return userMapper.toResponse(userRepository.save(user));
    }

    public void deleteUser(ObjectId id){
        UserEntity user = userRepository.findById(id).orElseThrow(UserNotFound::new);
        userRepository.delete(user);
    }

}
