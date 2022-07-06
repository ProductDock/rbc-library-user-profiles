package com.productdock.library.user.profiles.adapter.out.mongo;

import com.productdock.library.user.profiles.adapter.out.mongo.entity.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserAccountMongoRepository extends MongoRepository<UserProfile, String> {

    Optional<UserProfile> findByUserEmail(String userEmail);
}
