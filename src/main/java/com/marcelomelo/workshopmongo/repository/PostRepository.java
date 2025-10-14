package com.marcelomelo.workshopmongo.repository;

import com.marcelomelo.workshopmongo.domain.entities.PostEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<PostEntity, ObjectId> {
}
