package com.marcelomelo.workshopmongo.dtos.post;

import jakarta.validation.constraints.NotBlank;
import org.bson.types.ObjectId;

public record PostCreateDTO(

        ObjectId idUser,

        @NotBlank
        String title,

        @NotBlank
        String body

) {
}
