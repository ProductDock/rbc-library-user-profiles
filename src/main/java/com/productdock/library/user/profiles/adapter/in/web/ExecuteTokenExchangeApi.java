package com.productdock.library.user.profiles.adapter.in.web;

import com.productdock.library.user.profiles.application.port.in.ExchangeTokenUseCase;
import com.productdock.library.user.profiles.config.UserProfileAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/user-profiles/token")
public record ExecuteTokenExchangeApi(ExchangeTokenUseCase exchangeTokenUseCase) {

    @PostMapping
    public String exchangeTokens(UserProfileAuthenticationToken authentication) {
        return exchangeTokenUseCase.exchangeTokensFor(authentication);
    }
}
