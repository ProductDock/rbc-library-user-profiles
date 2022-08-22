package com.productdock.library.user.profiles.data.provider.adapter.out.mongo;

import com.productdock.library.user.profiles.adapter.out.mongo.entity.UserProfileEntity;

public class UserProfileEntityMother {

    private static final String defaultUserId = "1";
    private static final String defaultEmail = "::email::";
    private static final String defaultFullName = "::fullName";
    private static final String defaultProfilePicture = "::profilePicture::";
    private static final String defaultRole = "::role::";

    public static UserProfileEntity.UserProfileEntityBuilder defaultUserProfileEntityBuilder() {
        return UserProfileEntity.builder()
                .userId(defaultUserId)
                .userEmail(defaultEmail)
                .userFullName(defaultFullName)
                .userProfilePicture(defaultProfilePicture)
                .role(defaultRole);
    }

    public static UserProfileEntity defaultUserProfileEntity() {
        return defaultUserProfileEntityBuilder().build();
    }
}