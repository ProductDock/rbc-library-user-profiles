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

    public Map<String, Object> getTokenClaims() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("fullName", fullName);
        claims.put("email", email);
        claims.put("picture", profilePicture);
        claims.put("userId", userId);
        return claims;
    }
}
