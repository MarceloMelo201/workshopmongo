package com.marcelomelo.workshopmongo.mapper;

import com.marcelomelo.workshopmongo.domain.entities.PostEntity;
import com.marcelomelo.workshopmongo.dtos.post.PostResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostResponseDTO toResponse(PostEntity entity){
        return PostResponseDTO
                .builder()
                .idAuthor(entity.getUser().getIdUser())
                .date(entity.getDate())
                .title(entity.getTitle())
                .body(entity.getBody())
                .comments(entity.getComments())
                .build();
    }

}
