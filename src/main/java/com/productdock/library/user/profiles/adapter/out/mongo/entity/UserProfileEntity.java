package com.productdock.library.user.profiles.adapter.out.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("user-profiles")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileEntity {

    @Id
    private String userId;
    private String userFullName;
    @Indexed(unique=true)
    private String userEmail;
    private String userProfilePicture;
    private String role;
}
