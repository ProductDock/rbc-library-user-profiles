package com.productdock.library.user.profiles.adapter.in.web;

import com.productdock.library.user.profiles.application.port.in.ExchangeTokensUseCase;
import com.productdock.library.user.profiles.domain.UserAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.oauth2.jwt.Jwt;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("api/user-profiles/tokens/exchange")
public record ExecuteTokensExchangeApi(ExchangeTokensUseCase exchangeTokensUseCase) {

    @PostMapping
    public void exchangeTokens(HttpServletResponse response, Authentication authentication) {

        var userCredentials = ((Jwt) authentication.getCredentials());
        var userInfo = UserAccount.builder()
                .fullName(userCredentials.getClaim("fullName"))
                .email(userCredentials.getClaim("email"))
                .profilePicture(userCredentials.getClaim("imageUrl"))
                .build();

        String newToken = exchangeTokensUseCase.exchangeTokens(((Jwt) authentication.getCredentials()));
        response.setHeader(HttpHeaders.AUTHORIZATION, newToken);
    }
}
