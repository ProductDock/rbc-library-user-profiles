package com.productdock.library.user.profiles.adapter.out.mongo.mapper;

import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static com.productdock.library.user.profiles.data.provider.adapter.out.mongo.UserProfileEntityMother.defaultUserProfileEntityBuilder;
import static com.productdock.library.user.profiles.data.provider.domain.UserProfileMother.defaultUserProfile;
import static com.productdock.library.user.profiles.data.provider.adapter.out.mongo.UserProfileEntityMother.defaultUserProfileEntity;

class UserProfileMapperShould {

    private final UserProfileMapper userProfileMapper = Mappers.getMapper(UserProfileMapper.class);

    @Test
    void mapUserProfileEntityToUserProfile(){
        var userProfileEntity = defaultUserProfileEntity();

        var userProfile = userProfileMapper.toDomain(userProfileEntity);

        try(var softly = new AutoCloseableSoftAssertions()){
            softly.assertThat(userProfile.getUserId()).isEqualTo(userProfileEntity.getUserId());
            softly.assertThat(userProfile.getEmail()).isEqualTo(userProfileEntity.getUserEmail());
            softly.assertThat(userProfile.getFullName()).isEqualTo(userProfileEntity.getUserFullName());
            softly.assertThat(userProfile.getProfilePicture()).isEqualTo(userProfileEntity.getUserProfilePicture());
            softly.assertThat(userProfile.getRole()).isEqualTo(userProfileEntity.getRole());
        }
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
            try(var softly = new AutoCloseableSoftAssertions()){
                softly.assertThat(userProfiles.get(i).getUserId()).isEqualTo(userProfileEntities.get(i).getUserId());
                softly.assertThat(userProfiles.get(i).getEmail()).isEqualTo(userProfileEntities.get(i).getUserEmail());
                softly.assertThat(userProfiles.get(i).getFullName()).isEqualTo(userProfileEntities.get(i).getUserFullName());
                softly.assertThat(userProfiles.get(i).getProfilePicture()).isEqualTo(userProfileEntities.get(i).getUserProfilePicture());
                softly.assertThat(userProfiles.get(i).getRole()).isEqualTo(userProfileEntities.get(i).getRole());
            }
        }
    }

    @Test
    void mapUserProfileToUserProfileEntity(){
        var userProfile = defaultUserProfile();

        var userProfileEntity = userProfileMapper.toEntity(userProfile);

        try(var softly = new AutoCloseableSoftAssertions()){
            softly.assertThat(userProfileEntity.getUserId()).isEqualTo(userProfile.getUserId());
            softly.assertThat(userProfileEntity.getUserEmail()).isEqualTo(userProfile.getEmail());
            softly.assertThat(userProfileEntity.getUserFullName()).isEqualTo(userProfile.getFullName());
            softly.assertThat(userProfileEntity.getUserProfilePicture()).isEqualTo(userProfile.getProfilePicture());
            softly.assertThat(userProfileEntity.getRole()).isEqualTo(userProfile.getRole());
        }
    }
}