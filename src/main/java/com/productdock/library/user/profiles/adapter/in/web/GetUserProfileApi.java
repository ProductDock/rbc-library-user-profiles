package com.productdock.library.user.profiles.adapter.in.web;

import com.productdock.library.user.profiles.config.UserProfileAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-profiles/user-info")
public class GetUserProfileApi {

    @GetMapping
    public UserProfileDto getLoggedInUserProfile(UserProfileAuthenticationToken authentication) {
        var userProfile = authentication.getPrincipal();

        return new UserProfileDto(
                userProfile.getFullName(),
                userProfile.getProfilePicture(),
                userProfile.getEmail(),
                userProfile.getRole());
    }
}
