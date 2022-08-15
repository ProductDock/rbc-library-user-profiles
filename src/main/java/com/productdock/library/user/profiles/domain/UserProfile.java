package com.productdock.library.user.profiles.domain;

import lombok.Builder;

@Builder
public class UserProfile {

    public String userId;
    public String email;
    public String fullName;
    public String profilePicture;
    public String role;

}
