package com.productdock.library.user.profiles.application.port.out.persistence;

import com.productdock.library.user.profiles.domain.UserProfile;

import java.util.Collection;
import java.util.Optional;

public interface UserProfilePersistenceOutPort {

    Optional<UserProfile> findByUserEmail(String userEmail);

    Collection<UserProfile> findByUserEmails(Collection<String> userEmails);

    UserProfile save(UserProfile userProfile);
}
