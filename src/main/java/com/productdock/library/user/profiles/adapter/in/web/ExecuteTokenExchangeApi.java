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
@RequestMapping("api/user-profiles/access-token")
public record ExecuteTokenExchangeApi(ExchangeTokenUseCase exchangeTokenUseCase) {

    @PostMapping
    public void exchangeTokens(HttpServletResponse response, Authentication authentication) {
        var jwtToken = ((Jwt) authentication.getCredentials());
        var userAccount = UserProfile.builder()
                .fullName(jwtToken.getClaim("name"))
                .email(jwtToken.getClaim("email"))
                .profilePicture(jwtToken.getClaim("picture"))
                .build();
        String newToken = exchangeTokenUseCase.exchangeTokensFor(userAccount);
        response.setHeader(HttpHeaders.AUTHORIZATION, newToken);
    }
}
