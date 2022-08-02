package com.productdock.library.user.profiles.adapter.in.web;

import com.productdock.library.jwt.validator.UserTokenInfo;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-profiles/user-info")
public class GetUserProfileApi {

    @GetMapping
    public UserProfileDto getLoggedInUserProfile(Authentication authentication){
        var userTokenInfo = ((UserTokenInfo)authentication.getPrincipal());

        return new UserProfileDto(
                userTokenInfo.getFullName(),
                userTokenInfo.getPicture(),
                userTokenInfo.getEmail());
    }
}
