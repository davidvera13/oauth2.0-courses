package eu.dreamlabs.oauth.entities.clients;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
//import org.springframework.security.oauth2.core.AuthorizationGrantType;
//import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
//import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.time.Instant;
//import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "clients")
@Getter @Setter
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String clientId;

    private Instant clientIdIssuedAt;

    private String clientSecret;

    private Instant clientSecretExpiresAt;

    // private String clientName;

    //@Column(length = 1000)
    //private String clientAuthenticationMethods;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "client_authentication_methods",
            joinColumns = @JoinColumn(name = "client_id")
    )
    //private Set<ClientAuthenticationMethod> clientAuthenticationMethods;
    private Set<String> clientAuthenticationMethods;

    // @Column(length = 1000)
    // private String authorizationGrantTypes;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "authorization_grant_types",
            joinColumns = @JoinColumn(name = "client_id")
    )
    //private Set<AuthorizationGrantType> authorizationGrantTypes;
    private Set<String> authorizationGrantTypes;

    //@Column(length = 1000)
    //private String redirectUris;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "redirect_uris",
            joinColumns = @JoinColumn(name = "client_id")
    )
    private Set<String> redirectUris;

    // @Column(length = 1000)
    // private String scopes;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "scopes",
            joinColumns = @JoinColumn(name = "client_id")
    )
    private Set<String> scopes;

    private boolean requireProofKey;

    // @Column(length = 1000)
    // private String postLogoutRedirectUris;
    // @Column(length = 2000)
    // private String clientSettings;
    // @Column(length = 2000)
    // private String tokenSettings;
}