package com.productdock.library.user.profiles.adapter.in.web;

import com.productdock.library.user.profiles.application.port.in.GetUserProfileQuery;
import com.productdock.library.user.profiles.config.UserProfileAuthenticationToken;
import com.productdock.library.user.profiles.domain.UserProfile;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.productdock.library.user.profiles.data.provider.domain.UserProfileMother.defaultUserProfileBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class GetUserProfileApiShould {

    @InjectMocks
    private GetUserProfileApi getUserProfileApi;

    @Mock
    private GetUserProfileQuery getUserProfileQuery;

    @Mock
    private UserProfileMapper userProfileMapper;

    @Test
    void getLoggedInUserProfile() {
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

        assertThat(userProfileDto.email).isEqualTo(userTokenInfo.getEmail());
        assertThat(userProfileDto.fullName).isEqualTo(userTokenInfo.getFullName());
        assertThat(userProfileDto.image).isEqualTo(userTokenInfo.getProfilePicture());
        assertThat(userProfileDto.role).isEqualTo(userTokenInfo.getRole());
    }


    @Test
    void getUserProfilesByUserEmails() {
        var userEmails = List.of("::email1::");
        var userProfile = defaultUserProfileBuilder().userId("1").fullName("::name1::").email("::email1::").profilePicture("::image1::").build();
        var userProfiles = List.of(userProfile);

        var userProfileDto = new UserProfileDto("::name1::", "::image1::", "::email1::");
        var userProfileDtoCollection = List.of(userProfileDto);

        given(getUserProfileQuery.getByUserEmails(userEmails)).willReturn(userProfiles);
        given(userProfileMapper.toDtoCollection(userProfiles)).willReturn(userProfileDtoCollection);

        var users = getUserProfileApi.getByUserEmails(userEmails);

        for (int i = 0; i < users.size(); i++) {
            try (var softly = new AutoCloseableSoftAssertions()) {
                softly.assertThat(users.get(i).email).isEqualTo(userProfileDtoCollection.get(i).email);
                softly.assertThat(users.get(i).fullName).isEqualTo(userProfileDtoCollection.get(i).fullName);
                softly.assertThat(users.get(i).image).isEqualTo(userProfileDtoCollection.get(i).image);

            }
        }
    }

}