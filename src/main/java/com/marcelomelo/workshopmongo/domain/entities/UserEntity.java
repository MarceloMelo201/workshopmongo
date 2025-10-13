package com.marcelomelo.workshopmongo.domain.entities;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;


@Document(collection = "user_entity")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {

    @Id
    private ObjectId idUSer;
    private String name;
    private String email;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity userEntity = (UserEntity) o;
        return Objects.equals(idUSer, userEntity.idUSer);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idUSer);
    }
}
