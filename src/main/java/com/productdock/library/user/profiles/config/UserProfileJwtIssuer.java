package com.productdock.library.user.profiles.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class UserProfileJwtIssuer {

    @Value("${security.jwt.user-profile.known-issuer}")
    private String issuer;
}
