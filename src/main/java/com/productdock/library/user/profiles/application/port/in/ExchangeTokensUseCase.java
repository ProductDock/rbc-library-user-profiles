package com.productdock.library.user.profiles.application.port.in;

import org.springframework.security.oauth2.jwt.Jwt;

public interface ExchangeTokensUseCase {

    String exchangeTokens(Jwt jwtToken);
}
