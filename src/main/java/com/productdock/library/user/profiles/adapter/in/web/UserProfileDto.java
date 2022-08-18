package com.productdock.library.user.profiles.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class UserProfileDto {

    public String fullName;
    public String image;
    public String email;
}
