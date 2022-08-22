package com.productdock.library.user.profiles.data.provider.adapter.in.web;

import com.productdock.library.user.profiles.config.UserProfileAuthenticationToken;
import com.productdock.library.user.profiles.domain.UserProfile;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

import static com.productdock.library.user.profiles.data.provider.domain.UserProfileMother.defaultUserProfile;

public class AuthenticationProvider {

    private static final UserProfile DEFAULT_USER_PROFILE = defaultUserProfile();
    private static final String DEFAULT_TOKEN_VALUE = "token";

    public static Authentication defaultTokenAuthentication() {
        return defaultTokenAuthentication(DEFAULT_USER_PROFILE);
    }

    public static Authentication defaultTokenAuthentication(UserProfile userProfile) {
        var header = new HashMap<String, Object>();
        header.put("typ", "JWT");
        var claims = new HashMap<String, Object>();
        claims.put("email", userProfile.getEmail());

        return new UserProfileAuthenticationToken(
                new Jwt(DEFAULT_TOKEN_VALUE,
                        Instant.now(),
                        Instant.now(),
                        header,
                        claims),
                userProfile, new ArrayList<>());
    }


}
