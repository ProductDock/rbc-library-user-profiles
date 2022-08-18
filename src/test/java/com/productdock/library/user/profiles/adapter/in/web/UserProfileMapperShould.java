package com.productdock.library.user.profiles.adapter.in.web;

import com.productdock.library.user.profiles.domain.UserProfile;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static com.productdock.library.user.profiles.data.provider.domain.UserProfileMother.defaultUserProfile;
import static com.productdock.library.user.profiles.data.provider.domain.UserProfileMother.defaultUserProfileBuilder;

class UserProfileMapperShould {

    private final UserProfileMapper userProfileMapper = Mappers.getMapper(UserProfileMapper.class);

    @Test
    void mapUserProfileToUserProfileDto() {
        var userProfile = defaultUserProfile();

        var userProfileDto = userProfileMapper.toDto(userProfile);

        assertThatUserProfileMatchUserProfileDto(userProfile, userProfileDto);
    }

    @Test
    void mapUserProfilesToUserProfileDtoCollection() {
        var firstUser = defaultUserProfileBuilder().userId("1").fullName("::name1::").email("::email1::").build();
        var secondUser = defaultUserProfileBuilder().userId("2").fullName("::name2::").email("::email2::").build();
        var userProfiles = List.of(firstUser, secondUser);

        var userProfileDtoCollection = userProfileMapper.toDtoCollection(userProfiles);

        for (int i = 0; i < userProfileDtoCollection.size(); i++) {
            assertThatUserProfileMatchUserProfileDto(userProfiles.get(i), userProfileDtoCollection.get(i));
        }
    }

    private void assertThatUserProfileMatchUserProfileDto(UserProfile userProfile, UserProfileDto userProfileDto) {
        try (var softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat(userProfileDto.image).isEqualTo(userProfile.getProfilePicture());
            softly.assertThat(userProfileDto.fullName).isEqualTo(userProfile.getFullName());
            softly.assertThat(userProfileDto.email).isEqualTo(userProfile.getEmail());
        }
    }
}
