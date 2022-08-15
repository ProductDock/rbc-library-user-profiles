package com.productdock.library.user.profiles.application.service;

import com.productdock.library.user.profiles.config.UserProfileAuthenticationToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static com.productdock.library.user.profiles.data.provider.domain.UserProfileMother.defaultUserProfile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class TokenServiceShould {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private JwtEncoder encoder;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(tokenService, "userProfileJwtIssuer", "ok");
    }

    @Test
    void exchangeTokens() {
        var userProfileAuthenticationToken = mock(UserProfileAuthenticationToken.class);
        var userProfile = defaultUserProfile();
        var jwtToken = mock(Jwt.class);
        given(userProfileAuthenticationToken.getPrincipal()).willReturn(userProfile);
        given(encoder.encode(any())).willReturn(jwtToken);
        given(jwtToken.getTokenValue()).willReturn("::jwtToken::");

        var jwt = tokenService.exchangeTokensFor(userProfileAuthenticationToken);

        assertThat(jwt).isEqualTo("::jwtToken::");
    }
}
