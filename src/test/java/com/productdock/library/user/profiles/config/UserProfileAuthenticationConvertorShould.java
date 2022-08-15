package com.productdock.library.user.profiles.config;

import com.productdock.library.user.profiles.application.port.out.persistence.UserProfilePersistenceOutPort;
import com.productdock.library.user.profiles.domain.UserProfile;
import lombok.NonNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.*;
import java.util.stream.Stream;

import static com.productdock.library.user.profiles.data.provider.domain.UserProfileMother.defaultUserProfile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserProfileAuthenticationConvertorShould {

    @InjectMocks
    private UserProfileAuthenticationConvertor userProfileAuthenticationConvertor;

    @Mock
    private UserProfilePersistenceOutPort userProfilePersistenceOutPort;

    @Mock
    private Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter;

    @Test
    void exchangeTokensWhenUserProfileExist() {
        var jwtToken = mock(Jwt.class);
        var userProfile = Optional.of(mock(UserProfile.class));
        var email = "email";
        given(jwtToken.getClaim("email")).willReturn(email);
        given(userProfilePersistenceOutPort.findByUserEmail(email)).willReturn(userProfile);

        var abstractAuthenticationToken = userProfileAuthenticationConvertor.convert(jwtToken);

        assertThat(abstractAuthenticationToken.isAuthenticated()).isTrue();
    }
}
