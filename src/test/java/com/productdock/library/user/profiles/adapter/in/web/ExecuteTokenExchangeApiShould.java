package com.productdock.library.user.profiles.adapter.in.web;

import com.productdock.library.user.profiles.application.port.in.ExchangeTokenUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ExecuteTokenExchangeApiShould {

    @InjectMocks
    private ExecuteTokenExchangeApi executeTokenExchangeApi;

    @Mock
    private ExchangeTokenUseCase exchangeTokenUseCase;

    @Test
    void exchangeTokens() {
        var authentication = mock(Authentication.class);
        var response = new MockHttpServletResponse();
        var jwt = mock(Jwt.class);
        given(authentication.getCredentials()).willReturn(jwt);
        given(jwt.getClaim("name")).willReturn("::user::");
        given(jwt.getClaim("email")).willReturn("::email::");
        given(jwt.getClaim("picture")).willReturn("::picture::");
        given(exchangeTokenUseCase.exchangeTokensFor(any())).willReturn("::newToken::");

        executeTokenExchangeApi.exchangeTokens(response, authentication);

        assertThat(response.getHeader(HttpHeaders.AUTHORIZATION)).isEqualTo("::newToken::");
    }

}
