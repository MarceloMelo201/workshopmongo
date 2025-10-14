package com.marcelomelo.workshopmongo.dtos.comment;

import jakarta.validation.constraints.NotBlank;

public record CommentCreateDTO(

        @NotBlank
        String text
) {
}
