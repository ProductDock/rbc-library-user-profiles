package com.productdock.library.user.profiles.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class GetUserProfileApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Test
    void shouldGetLoggedInUserProfileData() throws Exception {
        var result = mockMvc.perform(post("/api/user-profiles/access-token")
                        .with(jwt().jwt(jwt -> {
                            jwt.claim("email", "::email::");
                            jwt.claim("name", "::fullName::");
                            jwt.claim("picture", "::profilePicture::");
                        })))
                .andReturn();

        mockMvc.perform(get("/api/user-profiles/user-info")
                .header("Authorization", "Bearer " + result.getResponse().getHeader("Authorization")))
                .andExpect(content().json("{\"name\":\"::fullName::\",\"imageUrl\":\"::profilePicture::\",\"email\":\"::email::\"}"));

    }

    @Test
    void shouldReturnUnauthorized_WhenAccessTokenIsNotExchanged() throws Exception {
        mockMvc.perform(get("/api/user-profiles/user-info")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isUnauthorized());

    }
}
