package com.productdock.library.user.profiles.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig {

	@Value("${security.jwt.user-profile.public.key}")
	RSAPublicKey key;

	@Value("${security.jwt.user-profile.private.key}")
	RSAPrivateKey priv;

	@Value("${security.jwt.user-profile.known-issuer}")
	private String userProfileJwtIssuer;

	@Value("${security.jwt.google.jwk-set-uri}")
	private String jwkSetUri;

	@Value("${security.jwt.google.known-issuer}")
	private String googleJwtIssuer;

	@Autowired
	private UserProfileAuthenticationConvertor userProfileAuthenticationConvertor;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(authorize -> authorize.antMatchers("/actuator/**").permitAll()
						.anyRequest().authenticated())
				.csrf(csrf -> csrf.ignoringAntMatchers("/token"))
				.oauth2ResourceServer(oauth2-> oauth2.authenticationManagerResolver(authenticationManagerResolver()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(exceptions -> exceptions
						.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
						.accessDeniedHandler(new BearerTokenAccessDeniedHandler())
				);
		return http.build();
	}

	@Bean
	public JwtIssuerAuthenticationManagerResolver authenticationManagerResolver() {
		Map<String, AuthenticationManager> authenticationManagers = new HashMap<>();
		authenticationManagers.put(userProfileJwtIssuer, pdAuthenticationManager());
		authenticationManagers.put(googleJwtIssuer, googleAuthenticationManager());
		return new JwtIssuerAuthenticationManagerResolver
						(authenticationManagers::get);
	}

	public AuthenticationManager pdAuthenticationManager() {
		JwtDecoder jwtDecoder = NimbusJwtDecoder.withPublicKey(this.key).build();
		var provider = new JwtAuthenticationProvider(jwtDecoder);
		provider.setJwtAuthenticationConverter(userProfileAuthenticationConvertor);
		return provider::authenticate;
	}

	public AuthenticationManager googleAuthenticationManager() {
		JwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(this.jwkSetUri).build();
		var provider = new JwtAuthenticationProvider(jwtDecoder);
		provider.setJwtAuthenticationConverter(userProfileAuthenticationConvertor);
		return provider::authenticate;
	}

	@Bean
	JwtEncoder jwtEncoder() {
		JWK jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
		JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
		return new NimbusJwtEncoder(jwks);
	}

}
