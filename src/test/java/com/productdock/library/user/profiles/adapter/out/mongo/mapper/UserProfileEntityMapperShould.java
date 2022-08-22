package com.productdock.library.user.profiles.adapter.out.mongo.mapper;

import com.productdock.library.user.profiles.adapter.out.mongo.entity.UserProfileEntity;
import com.productdock.library.user.profiles.domain.UserProfile;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static com.productdock.library.user.profiles.data.provider.adapter.out.mongo.UserProfileEntityMother.defaultUserProfileEntity;
import static com.productdock.library.user.profiles.data.provider.adapter.out.mongo.UserProfileEntityMother.defaultUserProfileEntityBuilder;
import static com.productdock.library.user.profiles.data.provider.domain.UserProfileMother.defaultUserProfile;

class UserProfileEntityMapperShould {

    private final UserProfileEntityMapper userProfileMapper = Mappers.getMapper(UserProfileEntityMapper.class);

    @Test
    void mapUserProfileEntityToUserProfile(){
        var userProfileEntity = defaultUserProfileEntity();

        var userProfile = userProfileMapper.toDomain(userProfileEntity);

        assertThatUserProfileEntityMatchUserProfile(userProfileEntity, userProfile);
    }

    @Test
    void mapUserProfileEntityCollectionToUserProfileCollection(){
        var firstEntity = defaultUserProfileEntity();
        var secondEntity = defaultUserProfileEntityBuilder()
                .userId("1")
                .userEmail("::email1::")
                .userProfilePicture("::image1::")
                .userFullName("::name1::").build();
        var userProfileEntities = List.of(firstEntity, secondEntity);

        var userProfiles = userProfileMapper.toDomainCollection(userProfileEntities).stream().toList();

        for (int i = 0; i < userProfiles.size(); i++) {
            assertThatUserProfileEntityMatchUserProfile(userProfileEntities.get(i), userProfiles.get(i));
        }
    }

    @Test
    void mapUserProfileToUserProfileEntity(){
        var userProfile = defaultUserProfile();

        var userProfileEntity = userProfileMapper.toEntity(userProfile);

        assertThatUserProfileEntityMatchUserProfile(userProfileEntity, userProfile);
    }

    private void assertThatUserProfileEntityMatchUserProfile(UserProfileEntity userProfileEntity, UserProfile userProfile) {
        try(var softly = new AutoCloseableSoftAssertions()){
            softly.assertThat(userProfileEntity.getUserId()).isEqualTo(userProfile.getUserId());
            softly.assertThat(userProfileEntity.getUserEmail()).isEqualTo(userProfile.getEmail());
            softly.assertThat(userProfileEntity.getUserFullName()).isEqualTo(userProfile.getFullName());
            softly.assertThat(userProfileEntity.getUserProfilePicture()).isEqualTo(userProfile.getProfilePicture());
            softly.assertThat(userProfileEntity.getRole()).isEqualTo(userProfile.getRole());
        }
    }
}