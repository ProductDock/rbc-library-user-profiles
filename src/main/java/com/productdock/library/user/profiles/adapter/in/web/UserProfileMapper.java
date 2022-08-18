package com.productdock.library.user.profiles.adapter.in.web;

import com.productdock.library.user.profiles.domain.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
interface UserProfileMapper {

    @Mapping(target = "image", source = "source.profilePicture")
    UserProfileDto toDto(UserProfile source);

    List<UserProfileDto> toDtoCollection(Collection<UserProfile> source);
}
