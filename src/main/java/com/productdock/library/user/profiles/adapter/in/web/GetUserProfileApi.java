package com.productdock.library.user.profiles.adapter.in.web;

import com.productdock.library.user.profiles.application.port.in.GetUserProfileQuery;
import com.productdock.library.user.profiles.config.UserProfileAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user-profiles")
public record GetUserProfileApi(GetUserProfileQuery getUserProfileQuery,
                                UserProfileMapper mapper) {

    @GetMapping("/user-info")
    public LoggedInUserDto getLoggedInUserProfile(UserProfileAuthenticationToken authentication) {
        var userProfile = authentication.getPrincipal();

        return new LoggedInUserDto(
                userProfile.getFullName(),
                userProfile.getProfilePicture(),
                userProfile.getEmail(),
                userProfile.getRole());
    }

    @GetMapping
    public List<UserProfileDto> getByUserEmails(@RequestParam List<String> userEmails) {
        var userProfiles = getUserProfileQuery.getByUserEmails(userEmails);

        return mapper.toDtoCollection(userProfiles);
    }
}
