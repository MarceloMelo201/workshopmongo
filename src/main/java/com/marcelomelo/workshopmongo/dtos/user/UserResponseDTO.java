package com.marcelomelo.workshopmongo.dtos.user;

import lombok.Builder;
import org.bson.types.ObjectId;

@Builder
public record UserResponseDTO(

        String idUser,
        String name,
        String email
) {
}
