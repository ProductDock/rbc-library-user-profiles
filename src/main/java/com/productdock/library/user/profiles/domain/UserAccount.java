package com.productdock.library.user.profiles.domain;

import lombok.Builder;

@Builder
public class UserAccount {

    public final String userId;
    public final String email;
    public final String fullName;
    public final String profilePicture;

}
