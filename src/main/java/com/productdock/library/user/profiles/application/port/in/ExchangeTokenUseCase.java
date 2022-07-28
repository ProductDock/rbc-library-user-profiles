package com.productdock.library.user.profiles.application.port.in;

import com.productdock.library.user.profiles.domain.UserProfile;

public interface ExchangeTokenUseCase {

    String exchangeTokensFor(UserProfile jwtToken);
}
