package com.productdock.library.user.profiles.data.provider.domain;

import com.productdock.library.user.profiles.domain.UserProfile;

public class UserProfileMother {
    private static final String defaultUserId = "1";
    private static final String defaultEmail = "::email::";
    private static final String defaultFullName = "::fullName::";
    private static final String defaultProfilePicture = "::profilePicture::";
    private static final String defaultRole = "::role::";

    public static UserProfile.UserProfileBuilder defaultUserProfileBuilder() {
        return UserProfile.builder()
                .userId(defaultUserId)
                .email(defaultEmail)
                .fullName(defaultFullName)
                .profilePicture(defaultProfilePicture)
                .role(defaultRole);
    }

    public static UserProfile defaultUserProfile() {
        return defaultUserProfileBuilder().build();
    }
}