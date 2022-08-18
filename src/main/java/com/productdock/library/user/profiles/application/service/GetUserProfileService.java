package com.productdock.library.user.profiles.application.service;

import com.productdock.library.user.profiles.application.port.in.GetUserProfileQuery;
import com.productdock.library.user.profiles.application.port.out.persistence.UserProfilePersistenceOutPort;
import com.productdock.library.user.profiles.domain.UserProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
record GetUserProfileService(UserProfilePersistenceOutPort userProfileRepository) implements GetUserProfileQuery {

    @Override
    public Collection<UserProfile> getByUserEmails(Collection<String> userEmails) {
        log.debug("Fetched user profile for user emails: {}", userEmails);
        return userProfileRepository.findByUserEmails(userEmails);
    }
}
