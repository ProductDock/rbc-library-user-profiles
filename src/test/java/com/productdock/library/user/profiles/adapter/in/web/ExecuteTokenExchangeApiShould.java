package com.productdock.library.user.profiles.adapter.in.web;

import com.productdock.library.user.profiles.application.port.in.ExchangeTokenUseCase;
import com.productdock.library.user.profiles.config.UserProfileAuthenticationToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
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
        var authentication = mock(UserProfileAuthenticationToken.class);
        given(exchangeTokenUseCase.exchangeTokensFor(authentication)).willReturn("::jwt::");

        var token = executeTokenExchangeApi.exchangeTokens(authentication);

        assertThat(token).isEqualTo("::jwt::");
    }

}