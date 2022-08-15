package com.productdock.library.user.profiles.application.port.out.persistence;

import com.productdock.library.user.profiles.domain.UserProfile;

import java.util.Optional;

public interface UserProfilePersistenceOutPort {

    Optional<UserProfile> findByUserEmail(String userEmail);

    UserProfile save(UserProfile userProfile);
}
