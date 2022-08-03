package com.productdock.library.user.profiles.domain;

import org.junit.jupiter.api.Test;

import static com.productdock.library.user.profiles.data.provider.domain.UserProfileMother.defaultUserProfile;
import static org.assertj.core.api.Assertions.assertThat;

class UserProfileShould {

    @Test
    void getTokenClaims(){
        var userProfile = defaultUserProfile();

        assertThat(userProfile.getTokenClaims()).extracting("fullName").isEqualTo("::fullName::");
        assertThat(userProfile.getTokenClaims()).extracting("email").isEqualTo("::email::");
        assertThat(userProfile.getTokenClaims()).extracting("picture").isEqualTo("::profilePicture::");
        assertThat(userProfile.getTokenClaims()).extracting("userId").isEqualTo("1");
    }
}
