package com.productdock.library.user.profiles.application.service;

import com.productdock.library.jwt.validator.JwtTokenProvider;
import com.productdock.library.user.profiles.application.port.in.ExchangeTokenUseCase;
import com.productdock.library.user.profiles.application.port.out.persistence.UserProfilePersistenceOutPort;
import com.productdock.library.user.profiles.domain.UserProfile;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class ExchangeTokenService implements ExchangeTokenUseCase {

    private UserProfilePersistenceOutPort userProfilePersistenceOutPort;
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String exchangeTokensFor(UserProfile userProfile) {
        var existingUserProfile = userProfilePersistenceOutPort.findByUserEmail(userProfile.email);

        if (existingUserProfile.isEmpty()) {
            var newUserProfile = userProfilePersistenceOutPort.save(userProfile);
            return jwtTokenProvider.createTokenWithClaims(newUserProfile.getTokenClaims());
        }

        return jwtTokenProvider.createTokenWithClaims(existingUserProfile.get().getTokenClaims());
    }
}
