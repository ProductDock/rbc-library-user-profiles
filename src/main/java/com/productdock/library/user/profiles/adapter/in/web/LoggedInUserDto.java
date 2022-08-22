package com.productdock.library.user.profiles.adapter.in.web;

public class LoggedInUserDto extends UserProfileDto {

    public String role;

    public LoggedInUserDto(String fullName, String image, String email, String role) {
        super(fullName, image, email);
        this.role = role;
    }
}
