package com.productdock.library.user.profiles.application.service;

import com.productdock.library.user.profiles.application.port.in.ExchangeTokenUseCase;
import com.productdock.library.user.profiles.config.UserProfileAuthenticationToken;
import com.productdock.library.user.profiles.domain.UserProfile;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class TokenService implements ExchangeTokenUseCase {

    private static final long TOKEN_DURATION = 36000L;
    private JwtEncoder encoder;

    @Override
    public String exchangeTokensFor(UserProfileAuthenticationToken authentication) {
        JwtClaimsSet claims = createClaimsFrom(authentication);
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private JwtClaimsSet createClaimsFrom(UserProfileAuthenticationToken authentication) {
        Instant now = Instant.now();
        var userProfile = authentication.getPrincipal();
        String scope = authoritiesToScope(authentication);
        return JwtClaimsSet.builder()
                .issuer("library.productdock.rs")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(TOKEN_DURATION))
                .subject(userProfile.userId)
                .claim("scope", scope)
                .claim("fullName", userProfile.fullName)
                .claim("email", userProfile.email)
                .claim("picture", userProfile.profilePicture)
                .build();
    }

    private String authoritiesToScope(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
    }
}
