package eu.dreamlabs.oauth.domains.request;

import lombok.*;

import java.util.Set;

@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ClientCreateRequest {
    private String clientId;
    private String clientSecret;
    // Cannot construct instance of `org.springframework.security.oauth2.core.ClientAuthenticationMethod`
    private Set<String> authenticationMethods;
    // Cannot construct instance of `org.springframework.security.oauth2.core.AuthorizationGrantType`
    private Set<String> authorizationGrantTypes;
    private Set<String> redirectUris;
    private Set<String> scopes;
    private Boolean requireProofKey;
}