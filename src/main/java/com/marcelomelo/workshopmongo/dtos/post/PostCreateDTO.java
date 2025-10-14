package com.marcelomelo.workshopmongo.dtos.post;

import jakarta.validation.constraints.NotBlank;

public record PostCreateDTO(

    @NotBlank
    String title,

    @NotBlank
    String body

) {
}
