package com.productdock.library.user.profiles.application.service;

import com.productdock.library.user.profiles.application.port.in.ExchangeTokenUseCase;
import com.productdock.library.user.profiles.config.UserProfileAuthenticationToken;
import com.productdock.library.user.profiles.domain.UserProfile;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
class TokenService implements ExchangeTokenUseCase {

    @Value("${security.jwt.user-profile.known-issuer}")
    private String userProfileJwtIssuer;

    private static final long TOKEN_DURATION = 36000L;
    @NonNull
    private JwtEncoder encoder;

    @Override
    public String exchangeTokensFor(UserProfileAuthenticationToken authentication) {
        var userProfile = authentication.getPrincipal();
        JwtClaimsSet claims = createClaimsFrom(userProfile);
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private JwtClaimsSet createClaimsFrom(UserProfile userProfile) {
        Instant now = Instant.now();
        return JwtClaimsSet.builder()
                .issuer(userProfileJwtIssuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(TOKEN_DURATION))
                .subject(userProfile.userId)
                .claim("scope", userProfile.role)
                .claim("fullName", userProfile.fullName)
                .claim("email", userProfile.email)
                .claim("picture", userProfile.profilePicture)
                .build();
    }
}
