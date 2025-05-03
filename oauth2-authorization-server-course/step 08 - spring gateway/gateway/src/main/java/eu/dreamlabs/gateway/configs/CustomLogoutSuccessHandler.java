package eu.dreamlabs.gateway.configs;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

public class CustomLogoutSuccessHandler implements ServerLogoutSuccessHandler {

    private final String postLogoutRedirectUri;

    public CustomLogoutSuccessHandler(String postLogoutRedirectUri) {
        this.postLogoutRedirectUri = postLogoutRedirectUri;
    }

    @Override
    public Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication) {
        // OidcIdToken idToken = (OidcIdToken) authentication.getCredentials();
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        // ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId()

        if (oAuth2AuthenticationToken != null) {
            // Extract the 'aud' claim (client_id)
            String clientId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            // Customize the logout URL with the client_id
            String logoutUrl = "http://localhost:9000/logout?client_id=" + clientId;

            // Set a redirect after logout (optional)
            exchange.getExchange().getResponse().setStatusCode(HttpStatus.FOUND);
            exchange.getExchange().getResponse().getHeaders().setLocation(URI.create(logoutUrl));

            return exchange.getExchange().getResponse().setComplete(); // Complete the response with the redirect
        }
        // Default logout behavior (if no 'aud' claim)
        return Mono.empty();
    }
}
