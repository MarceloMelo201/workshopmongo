package com.marcelomelo.workshopmongo.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "post_entity")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PostEntity {

    @Id
    private ObjectId idPost;

    private ObjectId authorId;
    private LocalDate date;
    private String title;
    private String body;

    private List<CommentEntity> comments = new ArrayList<>();
}
