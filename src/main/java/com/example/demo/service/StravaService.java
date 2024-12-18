package com.example.demo.service;

import javastrava.auth.AuthorisationService;
import javastrava.auth.impl.AuthorisationServiceImpl;
import javastrava.auth.model.Token;
import javastrava.model.StravaAthlete;
import javastrava.service.Strava;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

//@Service
public class StravaService {

    private static final String CLIENT_SECRET = "9a570c0924f99f4ff4a9b23c43464f1efa8ef255";
    private static final Integer CLIENT_ID = 130608;
    private static final String CODE = "aea0cea649ecdd2e37977d9935c143582ad5d83a";
    private static final String AUTH_CODE = "5cbf59878b60dc9d38b9a75145d67b1598e78a36";
    private static final String ACCESS = "61eba16ac02f547db98fdc4ba89b5ed7286ef5d3";
    private final RestTemplate restTemplate;

    private Token token;

    public StravaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void signIn() {
        AuthorisationService service = new AuthorisationServiceImpl();
        try {
            Token token = service.tokenExchange(CLIENT_ID, CLIENT_SECRET, ACCESS);
            System.out.println("Access token: " + token.getToken());
        } catch (Exception e) {
            System.out.println("foutje");
        }
    }

    public String getActivities(String accessToken) {
        String url = "https://www.strava.com/api/v3/athlete/activities";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        return response.getBody();
    }
}

