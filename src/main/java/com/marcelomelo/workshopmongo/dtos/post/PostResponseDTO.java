package com.marcelomelo.workshopmongo.dtos.post;

import com.marcelomelo.workshopmongo.domain.entities.CommentEntity;
import com.marcelomelo.workshopmongo.dtos.comment.AuthorDTO;

import lombok.Builder;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.List;

@Builder
public record PostResponseDTO(

        String idAuthor,

        String idPost,

        LocalDate date,

        String title,

        String body,

        List<CommentEntity> comments
) {
}
