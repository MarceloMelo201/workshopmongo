package com.marcelomelo.workshopmongo.dtos;

import lombok.Builder;
import org.bson.types.ObjectId;

@Builder
public record UserResponseDTO(

        ObjectId idUser,
        String name,
        String email
) {
}
