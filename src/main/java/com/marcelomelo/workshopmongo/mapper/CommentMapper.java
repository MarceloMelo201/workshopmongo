package com.marcelomelo.workshopmongo.mapper;

import com.marcelomelo.workshopmongo.domain.entities.CommentEntity;
import com.marcelomelo.workshopmongo.dtos.comment.CommentResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentResponseDTO toResponse(CommentEntity entity){
        return CommentResponseDTO
                .builder()
                .text(entity.getText())
                .date(entity.getDate())
                .author(entity.getAuthor())
                .build();
    }
}
