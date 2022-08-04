package com.productdock.library.user.profiles.application.port.in;

import org.springframework.security.core.Authentication;

public interface ExchangeTokenUseCase {

    String exchangeTokensFor(Authentication authentication);
}
