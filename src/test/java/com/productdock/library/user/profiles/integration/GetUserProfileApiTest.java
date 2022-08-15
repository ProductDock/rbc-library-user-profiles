package com.productdock.library.user.profiles.integration;

import com.productdock.library.user.profiles.adapter.in.web.GetUserProfileApi;
import com.productdock.library.user.profiles.config.UserProfileAuthenticationToken;
import com.productdock.library.user.profiles.domain.UserProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

import static com.productdock.library.user.profiles.data.provider.domain.UserProfileMother.defaultUserProfile;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GetUserProfileApiTest {

    private static final UserProfile USER_PROFILE =  defaultUserProfile();

    @Autowired
    private GetUserProfileApi getUserProfileApi;

    @Test
    void shouldExecuteTokenExchange_WhenUserProfile(){
        var header = new HashMap<String, Object>();
        header.put("typ", "JWT");
        var claims = new HashMap<String, Object>();
        claims.put("email", USER_PROFILE.email);
        var userProfileAuthenticationToken = new UserProfileAuthenticationToken(new Jwt("token", Instant.now(), Instant.now(), header, claims), USER_PROFILE, new ArrayList<>());

        var userProfileDto = getUserProfileApi.getLoggedInUserProfile(userProfileAuthenticationToken);

        assertThat(userProfileDto.email).isEqualTo(USER_PROFILE.email);
        assertThat(userProfileDto.name).isEqualTo(USER_PROFILE.fullName);
        assertThat(userProfileDto.imageUrl).isEqualTo(USER_PROFILE.profilePicture);
        assertThat(userProfileDto.role).isEqualTo(USER_PROFILE.role);
    }
}
