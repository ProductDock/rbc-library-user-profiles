package com.productdock.library.user.profiles.adapter.in.web;

import com.productdock.library.jwt.validator.UserTokenInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class GetUserProfileApiShould {

    @InjectMocks
    private GetUserProfileApi getUserProfileApi;

    @Test
    void getLoggedInUserProfile(){
        var authentication = mock(Authentication.class);
        var userTokenInfo = new UserTokenInfo("::id::", "::email::", "::fullName::", "::password::", "::picture::", Collections.emptyList());
        given(authentication.getPrincipal()).willReturn(userTokenInfo);

        var userProfileDto = (UserProfileDto)getUserProfileApi.getLoggedInUserProfile(authentication);

        assertThat(userProfileDto.email).isEqualTo(userTokenInfo.getEmail());
        assertThat(userProfileDto.name).isEqualTo(userTokenInfo.getFullName());
        assertThat(userProfileDto.imageUrl).isEqualTo(userTokenInfo.getPicture());
    }

}
