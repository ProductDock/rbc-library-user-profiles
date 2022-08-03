package com.productdock.library.user.profiles.integration;

import com.productdock.library.user.profiles.adapter.out.mongo.UserProfileMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.productdock.library.user.profiles.data.provider.out.mongo.UserProfileEntityMother.defaultUserProfileEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ExecuteTokenExchangeApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserProfileMongoRepository userProfileMongoRepository;

    @BeforeEach
    final void before() {
        userProfileMongoRepository.deleteAll();
    }

    @Test
    void shouldExecuteTokenExchange_WhenUserProfileNotExist() throws Exception {
        mockMvc.perform(post("/api/user-profiles/token")
                .with(jwt().jwt(jwt -> {
                    jwt.claim("email", "::email::");
                    jwt.claim("name", "::fullName::");
                    jwt.claim("picture", "::profilePicture::");
                })))
                .andExpect(header().exists("Authorization"));

        assertThat(userProfileMongoRepository.findByUserEmail("::email::")).isPresent();
    }

    @Test
    void shouldExecuteTokenExchange_WhenUserProfileExist() throws Exception {
        givenAnyUserProfile();

        mockMvc.perform(post("/api/user-profiles/token")
                        .with(jwt().jwt(jwt -> {
                            jwt.claim("email", "::email::");
                            jwt.claim("name", "::fullName::");
                            jwt.claim("picture", "::profilePicture::");
                        })))
                .andExpect(header().exists("Authorization"));

    }

    private void givenAnyUserProfile() {
        var userProfileEntity = defaultUserProfileEntity();
        userProfileMongoRepository.save(userProfileEntity);
    }

}
