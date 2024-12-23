package com.example.demo.keycloak;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
public class KeycloakTokenService {

    private final String keycloakUrl = "http://localhost:8080/realms/Drife/protocol/openid-connect/token";
    private final String clientId = "kortkort";
    private final String clientSecret = "geheim";

    public String refreshToken(String refreshToken) {
        RestTemplate restTemplate = new RestTemplate();

        // Stel de POST-request samen
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(keycloakUrl)
                .queryParam("grant_type", "refresh_token")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("refresh_token", refreshToken);

        // Verzend de request en ontvang het antwoord
        var response = restTemplate.postForObject(uriBuilder.toUriString(), null, Map.class);

        if (response != null && response.containsKey("access_token")) {
            return (String) response.get("access_token");
        } else {
            throw new RuntimeException("Unable to refresh token");
        }
    }
}

