package com.marcelomelo.workshopmongo.repository;

import com.marcelomelo.workshopmongo.domain.entities.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, UUID> {
}
