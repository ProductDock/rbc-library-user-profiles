package com.productdock.library.user.profiles.integration;

import com.productdock.library.user.profiles.adapter.out.mongo.UserProfileMongoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static com.productdock.library.user.profiles.data.provider.adapter.in.web.AuthenticationProvider.defaultTokenAuthentication;
import static com.productdock.library.user.profiles.data.provider.adapter.out.mongo.UserProfileEntityMother.defaultUserProfileEntityBuilder;
import static com.productdock.library.user.profiles.data.provider.domain.UserProfileMother.defaultUserProfile;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GetUserProfileApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserProfileMongoRepository userProfileMongoRepository;

    @Test
    void shouldGetLoggedInUserProfile() throws Exception {
        var userProfile = defaultUserProfile();
        mockMvc.perform(get("/api/user-profiles/user-info")
                        .with(csrf())
                        .with(authentication(defaultTokenAuthentication(userProfile)))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"email\":\"" + userProfile.getEmail() + "\"," +
                                "\"fullName\":\"" + userProfile.getFullName() + "\"," +
                                "\"image\":\"" + userProfile.getProfilePicture() + "\"," +
                                "\"role\":\"" + userProfile.getRole() + "\"}"));
    }

    @Test
    void shouldGetUserProfilesByUserEmails() throws Exception {
        givenUserProfileWithEmails("email1", "email2");

        makeGetUserProfilesByUserEmailsRequest("email1", "email2")
                .andExpect(jsonPath("$.[*].email", containsInAnyOrder("email1", "email2")));
    }

    @Test
    void shouldGetUserProfilesByUserEmailsWhenNoMatchingEmails() throws Exception {
        givenUserProfileWithEmails("email1", "email2");

        makeGetUserProfilesByUserEmailsRequest("email12", "email22")
                .andExpect(jsonPath("$.[*]", empty()));
    }

    private ResultActions makeGetUserProfilesByUserEmailsRequest(String... userEmails) throws Exception {
        return mockMvc.perform(get("/api/user-profiles")
                        .param("userEmails", userEmails)
                        .with(jwt().jwt(jwt -> {
                            jwt.claim("email", "::email::");
                            jwt.claim("name", "::userFullName::");
                        }))
                )
                .andExpect(status().isOk());
    }

    private void givenUserProfileWithEmails(String... userEmails) {
        for (var userEmail : userEmails) {
            var userProfile = defaultUserProfileEntityBuilder().userId(UUID.randomUUID().toString()).userEmail(userEmail).build();
            userProfileMongoRepository.save(userProfile);
        }
    }
}
