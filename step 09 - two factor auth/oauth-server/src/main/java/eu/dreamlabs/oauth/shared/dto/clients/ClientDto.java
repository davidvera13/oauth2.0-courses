package eu.dreamlabs.oauth.shared.dto.clients;

import lombok.*;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.time.Instant;
import java.util.Set;

@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ClientDto {
    private String clientId;
    private Instant clientIdIssuedAt;
    private String clientSecret;
    private Instant clientSecretExpiresAt;
    // private String clientName;
    private Set<ClientAuthenticationMethod> clientAuthenticationMethods;
    private Set<AuthorizationGrantType> authorizationGrantTypes;
    private Set<String> redirectUris;
    private Set<String> postLogoutRedirectUris;
    private Set<String> scopes;
    private boolean requireProofKey;
    // private String postLogoutRedirectUris;
    // private String clientSettings;
    // private String tokenSettings;
}