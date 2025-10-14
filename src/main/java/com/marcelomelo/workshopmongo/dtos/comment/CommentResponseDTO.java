package com.marcelomelo.workshopmongo.dtos.comment;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CommentResponseDTO(

        String text,
        LocalDate date,
        AuthorDTO author

) {
}
