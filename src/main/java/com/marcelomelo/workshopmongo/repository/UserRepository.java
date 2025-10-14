package com.marcelomelo.workshopmongo.repository;

import com.marcelomelo.workshopmongo.domain.entities.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {
    boolean existsByEmail(String email);
}
