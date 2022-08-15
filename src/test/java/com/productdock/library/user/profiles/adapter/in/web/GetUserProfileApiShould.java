package com.productdock.library.user.profiles.adapter.in.web;

import com.productdock.library.user.profiles.config.UserProfileAuthenticationToken;
import com.productdock.library.user.profiles.domain.UserProfile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class GetUserProfileApiShould {

    @InjectMocks
    private GetUserProfileApi getUserProfileApi;

    @Test
    void getLoggedInUserProfile(){
        var authentication = mock(UserProfileAuthenticationToken.class);
        var userTokenInfo = UserProfile.builder()
                .userId("::id::")
                .email("::email::")
                .fullName("::fullName::")
                .profilePicture("::picture::")
                .role("::role::")
                .build();
        given(authentication.getPrincipal()).willReturn(userTokenInfo);

        var userProfileDto = getUserProfileApi.getLoggedInUserProfile(authentication);

        assertThat(userProfileDto.email).isEqualTo(userTokenInfo.email);
        assertThat(userProfileDto.name).isEqualTo(userTokenInfo.fullName);
        assertThat(userProfileDto.imageUrl).isEqualTo(userTokenInfo.profilePicture);
        assertThat(userProfileDto.role).isEqualTo(userTokenInfo.role);
    }

}