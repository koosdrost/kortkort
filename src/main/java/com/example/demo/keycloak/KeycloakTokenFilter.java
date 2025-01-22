package com.example.demo.keycloak;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.Principal;
import java.time.Instant;

@Component
public class KeycloakTokenFilter implements Filter {

    private final KeycloakTokenService tokenService;

    public KeycloakTokenFilter(KeycloakTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Haal het huidige access token uit de request (bijv. uit headers of sessie)
        String accessToken = getAccessTokenFromRequest(httpRequest);

        if (accessToken != null && isTokenExpired(httpRequest)) {
            String refreshToken = getRefreshTokenFromSession(httpRequest);

            // Vernieuw het access token met de KeycloakTokenService
            String newAccessToken = tokenService.refreshToken(refreshToken);

            // Sla het vernieuwde access token op (bijv. in sessie of header)
            saveNewAccessTokenToSession(httpRequest, newAccessToken);
        }
        chain.doFilter(request, response); // Ga door met de request
    }

    private boolean isTokenExpired(HttpServletRequest request) {
        // Controleer of het token verlopen is, gebruik bijv. de 'exp'-claim van de JWT
        boolean expired = false;
        if (request.getUserPrincipal() != null) {
            OAuth2AuthenticationToken p = ((OAuth2AuthenticationToken) request.getUserPrincipal());
            DefaultOidcUser user = (DefaultOidcUser) p.getPrincipal();
            if (user.getExpiresAt().isBefore(Instant.now())) {
                expired = true;
            }
        }
        return expired;
    }

    private String getAccessTokenFromRequest(HttpServletRequest request) {
        String token = null;
        if (request.getUserPrincipal() != null) {
            OAuth2AuthenticationToken p = ((OAuth2AuthenticationToken) request.getUserPrincipal());
            DefaultOidcUser user = (DefaultOidcUser) p.getPrincipal();
            token = user.getIdToken().getTokenValue();
        }
        return token;
    }

    private String getRefreshTokenFromSession(HttpServletRequest request) {
        // Haal het refresh token uit de sessie of andere opslag
        String token = null;
        if (request.getUserPrincipal() != null) {
            Principal principal = request.getUserPrincipal();
            OAuth2AuthenticationToken p = ((OAuth2AuthenticationToken) principal);
            DefaultOidcUser user = (DefaultOidcUser) p.getPrincipal();
            token = user.getIdToken().getTokenValue();
        }
        return token;
    }

    private void saveNewAccessTokenToSession(HttpServletRequest request, String newAccessToken) {
        // Sla het nieuwe access token op (bijv. in de sessie)
    }
}
