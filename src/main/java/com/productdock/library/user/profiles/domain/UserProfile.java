package com.productdock.library.user.profiles.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserProfile {

    private String userId;
    private String email;
    private String fullName;
    private String profilePicture;
    private String role;

}
