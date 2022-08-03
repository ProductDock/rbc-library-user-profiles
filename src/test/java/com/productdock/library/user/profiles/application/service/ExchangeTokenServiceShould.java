package com.productdock.library.user.profiles.application.service;

import com.productdock.library.jwt.validator.JwtTokenProvider;
import com.productdock.library.user.profiles.application.port.out.persistence.UserProfilePersistenceOutPort;
import com.productdock.library.user.profiles.domain.UserProfile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ExchangeTokenServiceShould {

    @InjectMocks
    private ExchangeTokenService exchangeTokenService;

    @Mock
    private UserProfilePersistenceOutPort userProfilePersistenceOutPort;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void exchangeTokensWhenUserProfileExist() {
        var userProfile = mock(UserProfile.class);
        var existingUserProfile = Optional.of(mock(UserProfile.class));
        given(userProfilePersistenceOutPort.findByUserEmail(userProfile.email)).willReturn(existingUserProfile);
        given(jwtTokenProvider.createTokenWithClaims(existingUserProfile.get().getTokenClaims())).willReturn("::jwtToken::");

        var jwt = exchangeTokenService.exchangeTokensFor(userProfile);

        assertThat(jwt).isEqualTo("::jwtToken::");
    }

    @Test
    void saveUserProfileAndExchangeTokensWhenUserProfileNotExist() {
        var userProfile = mock(UserProfile.class);
        given(userProfilePersistenceOutPort.findByUserEmail(userProfile.email)).willReturn(Optional.empty());
        given(userProfilePersistenceOutPort.save(userProfile)).willReturn(userProfile);
        given(jwtTokenProvider.createTokenWithClaims(userProfile.getTokenClaims())).willReturn("::jwtToken::");

        var jwt = exchangeTokenService.exchangeTokensFor(userProfile);

        assertThat(jwt).isEqualTo("::jwtToken::");
    }
}
