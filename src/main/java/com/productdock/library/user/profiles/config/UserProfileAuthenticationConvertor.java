package com.productdock.library.user.profiles.config;

import java.util.Collection;

import com.productdock.library.user.profiles.application.port.out.persistence.UserProfilePersistenceOutPort;
import com.productdock.library.user.profiles.domain.UserProfile;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserProfileAuthenticationConvertor implements Converter<Jwt, AbstractAuthenticationToken> {

  @NonNull
  private UserProfilePersistenceOutPort userProfilePersistenceOutPort;

  private Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

  @Override
  public final AbstractAuthenticationToken convert(Jwt jwt) {
    Collection<GrantedAuthority> authorities = this.jwtGrantedAuthoritiesConverter.convert(jwt);

    UserProfile principal = createNewProfileOrLoadExistingOne(jwt);
    return new UserProfileAuthenticationToken(jwt, principal, authorities);
  }

  private UserProfile createNewProfileOrLoadExistingOne(Jwt jwt) {
    var existingUserProfile = userProfilePersistenceOutPort.findByUserEmail(jwt.getClaim("email"));
    if (existingUserProfile.isPresent()) {
      return existingUserProfile.get();
    }
    return userProfilePersistenceOutPort.save(userProfileFromJwt(jwt));
  }

  private UserProfile userProfileFromJwt(Jwt jwt) {
    return UserProfile.builder()
            .fullName(jwt.getClaim("name"))
            .email(jwt.getClaim("email"))
            .profilePicture(jwt.getClaim("picture"))
            .build();
  }

}