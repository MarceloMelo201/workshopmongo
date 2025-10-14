package com.marcelomelo.workshopmongo.dtos.comment;

import org.bson.types.ObjectId;

public record AuthorDTO(

        ObjectId idAuthor,

        String name
) {
}
