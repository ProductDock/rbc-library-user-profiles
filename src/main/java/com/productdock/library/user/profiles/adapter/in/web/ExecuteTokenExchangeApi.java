package com.productdock.library.user.profiles.adapter.in.web;

import com.productdock.library.user.profiles.application.port.in.ExchangeTokenUseCase;
import com.productdock.library.user.profiles.domain.UserProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("api/user-profiles/token")
public record ExecuteTokenExchangeApi(ExchangeTokenUseCase exchangeTokenUseCase) {

    @PostMapping
    public String exchangeTokens(HttpServletResponse response, Authentication authentication) {
        return exchangeTokenUseCase.exchangeTokensFor(authentication);
    }
}
