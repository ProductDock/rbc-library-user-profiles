package com.productdock.library.user.profiles.application.service;

import com.productdock.library.user.profiles.application.port.out.persistence.UserProfilePersistenceOutPort;
import com.productdock.library.user.profiles.domain.UserProfile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class GetUserProfileServiceShould {

    @InjectMocks
    private GetUserProfileService getUserProfileService;

    @Mock
    private UserProfilePersistenceOutPort repository;

    @Test
    void getUserProfilesByUserEmails() {
        var userEmails = List.of("::email1::", "::email1::");
        var firstUser = mock(UserProfile.class);
        var secondUser = mock(UserProfile.class);

        given(repository.findByUserEmails(userEmails)).willReturn(List.of(firstUser, secondUser));

        var userProfiles = getUserProfileService.getByUserEmails(userEmails);

        assertThat(userProfiles).hasSize(2);
    }
}
