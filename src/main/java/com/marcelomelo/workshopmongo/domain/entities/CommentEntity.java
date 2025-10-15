package com.marcelomelo.workshopmongo.domain.entities;

import com.marcelomelo.workshopmongo.dtos.comment.AuthorDTO;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "comment_entity")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentEntity {

    @Id
    private ObjectId idComment;

    private String text;
    private LocalDate date;
    private ObjectId idPost;
    private AuthorDTO author;

}
