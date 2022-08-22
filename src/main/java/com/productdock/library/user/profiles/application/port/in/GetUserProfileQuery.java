package com.productdock.library.user.profiles.application.port.in;

import com.productdock.library.user.profiles.domain.UserProfile;

import java.util.Collection;

public interface GetUserProfileQuery {

    Collection<UserProfile> getByUserEmails(Collection<String> userEmails);
}
