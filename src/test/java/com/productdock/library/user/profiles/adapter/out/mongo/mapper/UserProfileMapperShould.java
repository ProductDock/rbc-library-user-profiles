package com.productdock.library.user.profiles.adapter.out.mongo.mapper;

import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.productdock.library.user.profiles.data.provider.domain.UserProfileMother.defaultUserProfile;
import static com.productdock.library.user.profiles.data.provider.out.mongo.UserProfileEntityMother.defaultUserProfileEntity;

class UserProfileMapperShould {

    private final UserProfileMapper userProfileMapper = Mappers.getMapper(UserProfileMapper.class);

    @Test
    void mapUserProfileEntityToUserProfile(){
        var userProfileEntity = defaultUserProfileEntity();

        var userProfile = userProfileMapper.toDomain(userProfileEntity);

        try(var softly = new AutoCloseableSoftAssertions()){
            softly.assertThat(userProfile.userId).isEqualTo(userProfileEntity.getUserId());
            softly.assertThat(userProfile.email).isEqualTo(userProfileEntity.getUserEmail());
            softly.assertThat(userProfile.fullName).isEqualTo(userProfileEntity.getUserFullName());
            softly.assertThat(userProfile.profilePicture).isEqualTo(userProfileEntity.getUserProfilePicture());
        }
    }

    @Test
    void mapUserProfileToUserProfileEntity(){
        var userProfile = defaultUserProfile();

        var userProfileEntity = userProfileMapper.toEntity(userProfile);

        try(var softly = new AutoCloseableSoftAssertions()){
            softly.assertThat(userProfileEntity.getUserId()).isEqualTo(userProfile.userId);
            softly.assertThat(userProfileEntity.getUserEmail()).isEqualTo(userProfile.email);
            softly.assertThat(userProfileEntity.getUserFullName()).isEqualTo(userProfile.fullName);
            softly.assertThat(userProfileEntity.getUserProfilePicture()).isEqualTo(userProfile.profilePicture);
        }
    }
}
