package com.productdock.library.user.profiles.application.port.in;

import com.productdock.library.user.profiles.config.UserProfileAuthenticationToken;

public interface ExchangeTokenUseCase {

    String exchangeTokensFor(UserProfileAuthenticationToken authentication);
}
