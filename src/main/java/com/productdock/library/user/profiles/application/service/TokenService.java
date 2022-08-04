package com.productdock.library.user.profiles.application.service;

import com.productdock.library.user.profiles.application.port.in.ExchangeTokenUseCase;
import com.productdock.library.user.profiles.application.port.out.persistence.UserProfilePersistenceOutPort;
import com.productdock.library.user.profiles.domain.UserProfile;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class TokenService implements ExchangeTokenUseCase {

    private JwtEncoder encoder;

    @Override
    public String exchangeTokensFor(Authentication authentication) {
        Instant now = Instant.now();
        long expiry = 36000L;
        var userProfile = (UserProfile) authentication.getPrincipal();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("library.productdock.rs")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authentication.getName())
                .claim("scope", scope)
                .claim("fullName", userProfile.fullName)
                .claim("email", userProfile.email)
                .claim("picture", userProfile.profilePicture)
                .claim("userId", userProfile.userId)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
