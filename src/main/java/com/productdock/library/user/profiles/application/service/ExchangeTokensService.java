package com.productdock.library.user.profiles.application.service;

import com.productdock.library.user.profiles.application.port.in.ExchangeTokensUseCase;
import com.productdock.library.user.profiles.application.port.out.persistence.UserAccountPersistenceOutPort;
import com.productdock.library.user.profiles.domain.UserAccount;
import com.productdock.library.user.profiles.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class ExchangeTokensService implements ExchangeTokensUseCase {

    private UserAccountPersistenceOutPort userAccountPersistenceOutPort;
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String exchangeTokens(Jwt jwtToken) {
        var userAccount = UserAccount.builder()
                .fullName(jwtToken.getClaim("name"))
                .email(jwtToken.getClaim("email"))
                .profilePicture(jwtToken.getClaim("picture"))
                .build();
        var existingUserAccount = userAccountPersistenceOutPort.findByUserEmail(userAccount.email);
        if (existingUserAccount.isEmpty()) {
            userAccountPersistenceOutPort.save(userAccount);
        }

        return jwtTokenProvider.createToken(userAccount);
    }
}
