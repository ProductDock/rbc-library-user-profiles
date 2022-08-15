package com.productdock.library.user.profiles.adapter.out.mongo.mapper;

import com.productdock.library.user.profiles.adapter.out.mongo.entity.UserProfileEntity;
import com.productdock.library.user.profiles.domain.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserProfileMapper {

    @Mapping(target = "email", source = "source.userEmail")
    @Mapping(target = "profilePicture", source = "source.userProfilePicture")
    @Mapping(target = "fullName", source = "source.userFullName")
    UserProfile toDomain(UserProfileEntity source);

    @Mapping(target = "userEmail", source = "source.email")
    @Mapping(target = "userProfilePicture", source = "source.profilePicture")
    @Mapping(target = "userFullName", source = "source.fullName")
    UserProfileEntity toEntity(UserProfile source);

}
