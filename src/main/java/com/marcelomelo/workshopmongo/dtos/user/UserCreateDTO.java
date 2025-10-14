package com.marcelomelo.workshopmongo.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateDTO(

        @NotBlank
        String name,

        @Email
        String email
) {
}
