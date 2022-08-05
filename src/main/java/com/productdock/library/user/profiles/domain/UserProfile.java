package com.productdock.library.user.profiles.domain;

import lombok.Builder;

import java.util.HashMap;
import java.util.Map;

@Builder
public class UserProfile {

    public String userId;
    public String email;
    public String fullName;
    public String profilePicture;
    public String role;

}
