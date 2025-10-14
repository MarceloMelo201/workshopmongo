package com.marcelomelo.workshopmongo.mapper;

import com.marcelomelo.workshopmongo.domain.entities.UserEntity;
import com.marcelomelo.workshopmongo.dtos.user.UserCreateDTO;
import com.marcelomelo.workshopmongo.dtos.user.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(UserCreateDTO dto){
        return UserEntity
                .builder()
                .name(dto.name())
                .email(dto.email())
                .build();
    }

    public UserResponseDTO toResponse(UserEntity entity){
        return UserResponseDTO
                .builder()
                .idUser(entity.getIdUSer())
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
    }
}
