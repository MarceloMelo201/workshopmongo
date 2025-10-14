package com.marcelomelo.workshopmongo.mapper;

import com.marcelomelo.workshopmongo.domain.entities.UserEntity;
import com.marcelomelo.workshopmongo.dtos.UserCreateDTO;
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
}
