package com.productdock.library.user.profiles.integration;

import com.productdock.library.user.profiles.adapter.in.web.ExecuteTokenExchangeApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.test.web.servlet.MockMvc;

import java.security.interfaces.RSAPublicKey;

import static com.productdock.library.user.profiles.data.provider.adapter.in.web.AuthenticationProvider.defaultTokenAuthentication;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExecuteTokenExchangeApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ExecuteTokenExchangeApi executeTokenExchangeApi;

    @Value("${security.jwt.user-profile.public.key}")
    RSAPublicKey key;

    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.key).build();
    }

    @Test
    void shouldExecuteTokenExchange() throws Exception {
        var response = mockMvc.perform(post("/api/user-profiles/token")
                        .with(csrf())
                        .with(authentication(defaultTokenAuthentication()))
                )
                .andExpect(status().isOk()).andReturn().getResponse();

        assertDoesNotThrow(() -> response.getContentAsString());
        assertDoesNotThrow(() -> jwtDecoder().decode(response.getContentAsString()));
    }
}