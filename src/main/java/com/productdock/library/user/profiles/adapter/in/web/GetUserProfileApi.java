package com.productdock.library.user.profiles.adapter.in.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-profiles/user-info")
public class GetUserProfileApi {

    @GetMapping
    public UserProfileDto getLoggedInUserProfile() {
        //should return user info for user passed in request
        return null;
    }
}
