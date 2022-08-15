package com.productdock.library.user.profiles.config;

import com.productdock.library.user.profiles.domain.UserProfile;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Transient;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.Assert;

import java.util.Collection;

@Transient
public class UserProfileAuthenticationToken extends AbstractAuthenticationToken {

  private UserProfile principal;
  private Object credentials;
  private Jwt token;

  public UserProfileAuthenticationToken(Jwt token, UserProfile principal,
                                        Collection<? extends GrantedAuthority> authorities) {

    super(authorities);
    Assert.notNull(token, "token cannot be null");
    Assert.notNull(principal, "principal cannot be null");
    this.setAuthenticated(true);
    this.principal = principal;
    this.credentials = token;
    this.token = token;
  }

  @Override
  public Object getCredentials() {
    return credentials;
  }

  @Override
  public UserProfile getPrincipal() {
    return this.principal;
  }

  public final Jwt getToken() {
    return this.token;
  }
}
