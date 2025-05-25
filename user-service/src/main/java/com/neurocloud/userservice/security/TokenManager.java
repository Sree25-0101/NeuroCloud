package com.neurocloud.userservice.security;

import com.neurocloud.userservice.config.AuthTokenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.Instant;
import java.util.Map;

@Component
public class TokenManager {

    private final RestTemplate restTemplate = new RestTemplate();
    private final AuthTokenProperties authTokenProperties;

    private String cachedAccessToken;       // ⏳ the token
    private Instant tokenExpiryTime;         // ⏰ the expiry time

    @Autowired
    public TokenManager(AuthTokenProperties authTokenProperties) {
        this.authTokenProperties = authTokenProperties;
    }

    public synchronized String getAccessToken() {

        // 🛡 Check if token exists and is not expired
        if (cachedAccessToken != null && tokenExpiryTime != null && Instant.now().isBefore(tokenExpiryTime)) {
            // ✅ Token is still valid, return from cache
            return cachedAccessToken;
        }

        // 🔄 Token is missing or expired, fetch a new one
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(authTokenProperties.getClientId(), authTokenProperties.getClientSecret());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(authTokenProperties.getUrl(), request, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            cachedAccessToken = (String) response.getBody().get("access_token");

            // Set expiry time safely (e.g., token is valid for 10 min, we refresh 1 min earlier)
            int expiresInSeconds = (Integer) response.getBody().get("expires_in");
            tokenExpiryTime = Instant.now().plusSeconds(expiresInSeconds - 60); // Subtract 1 minute buffer
            return cachedAccessToken;
        } else {
            throw new RuntimeException("Failed to retrieve access token: " + response.getStatusCode());
        }
    }
}

