package com.example.demo.keycloak;

import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Component
public class KeycloakTokenService {

    private final String TOKEN_URL = "http://localhost:8080/realms/Drife/protocol/openid-connect/token";
    private final String CLIENT_ID = "kortkort";
    private final String CLIENT_SECRET = "geheim";

    public String refreshToken(String REFRESH_TOKEN) {
        HttpClient client = HttpClient.newHttpClient();

        String requestBody = "grant_type=refresh_token" +
                "&client_id=" + URLEncoder.encode(CLIENT_ID, StandardCharsets.UTF_8) +
                "&client_secret=" + URLEncoder.encode(CLIENT_SECRET, StandardCharsets.UTF_8) +
                "&refresh_token=" + URLEncoder.encode(REFRESH_TOKEN, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TOKEN_URL))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            // Verzend de request en ontvang het antwoord
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            throw new RuntimeException("Unable to refresh token");
        }
    }
}

