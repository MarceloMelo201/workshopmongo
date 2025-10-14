package com.marcelomelo.workshopmongo.repository;

import com.marcelomelo.workshopmongo.domain.entities.CommentEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<CommentEntity, ObjectId> {
}
