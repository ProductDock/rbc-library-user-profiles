package com.productdock.library.user.profiles.adapter.out.mongo;

import com.productdock.library.user.profiles.adapter.out.mongo.entity.UserProfileEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserProfileMongoRepository extends MongoRepository<UserProfileEntity, String> {

    Optional<UserProfileEntity> findByUserEmail(String userEmail);
}
