package com.neurocloud.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Duration;
import java.util.UUID;
import java.util.Arrays;

@Configuration
public class AuthorizationServerConfig {

    // ✅ Define first client for frontend login using password and client_credentials flow
    private RegisteredClient neurocloudClient() {
        return RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("neurocloud-client")
                .clientSecret(passwordEncoder().encode("neurocloud-secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)         // User login
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS) // Optional
                .scope("read")
                .scope("write")
                .tokenSettings(tokenSettings())
                .build();
    }

    // ✅ Define second client strictly for inter-service communication, i.e service-service calls
    private RegisteredClient internalServiceClient() {
        return RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("internal-service")
                .clientSecret(passwordEncoder().encode("internal-secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("internal")
                .tokenSettings(tokenSettings())
                .build();
    }

    // ✅ Register both clients in memory
    @Bean
    public InMemoryRegisteredClientRepository registeredClientRepository() {
        return new InMemoryRegisteredClientRepository(Arrays.asList(
                neurocloudClient(),
                internalServiceClient()
        ));
    }

    // ✅ Password encoder for users and client secrets
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ Core OAuth2 Authorization Server config (exposes /oauth2/token, etc.)
    @Bean
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults()); // Enables OpenID Connect endpoints (optional)
        return http.build();
    }

    // ✅ Default security config (protects other endpoints and allows form login)
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

    // ✅ Simple in-memory user for password grant type testing
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("testuser")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    // ✅ Server settings: set issuer URL for token validation
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer("http://localhost:8081") // Must match introspection & gateway config
                .build();
    }

    /*
     ℹ️  adds scope to the claims of access token
      This method because we are running into issue, well not issue, but the situation where in the response
      of the postman/bruno hit for:
      http://localhost:8081/oauth2/token
      in "body": "Form URL Encoded"--> key=grant_type value=client_credentials
         "Auth": username: neurocloud-client , password: neurocloud-secret
      */
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return context -> {
            if (context.getTokenType().getValue().equals("access_token")) {
                context.getClaims().claim("scope", String.join(" ", context.getAuthorizedScopes()));
            }
        };
    }

    // ✅ Common token settings for both clients
    private org.springframework.security.oauth2.server.authorization.settings.TokenSettings tokenSettings() {
        return org.springframework.security.oauth2.server.authorization.settings.TokenSettings.builder()
                .accessTokenTimeToLive(Duration.ofMinutes(10))
                .build();
    }
}
