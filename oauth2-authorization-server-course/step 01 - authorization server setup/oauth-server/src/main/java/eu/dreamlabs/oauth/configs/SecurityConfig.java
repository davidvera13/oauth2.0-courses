package eu.dreamlabs.oauth.configs;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;

    /**
     * 1. A Spring Security filter chain for the Protocol Endpoints.
     * @param http httpSecurity
     * @return a security filter chain
     * @throws Exception exception
     */
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(
            HttpSecurity http)
            throws Exception {
        log.info("# authorizationServerSecurityFilterChain() bean called");
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                OAuth2AuthorizationServerConfigurer.authorizationServer();
        return http
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, (authorizationServer) ->
                        authorizationServer
                                .oidc(Customizer.withDefaults()))	// Enable OpenID Connect 1.0
                .authorizeHttpRequests((authorize) ->
                        authorize.anyRequest().authenticated())
                // Redirect to the login page when not authenticated from the
                // authorization endpoint
                .exceptionHandling((exceptions) -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)))
                // Accept access tokens for User Info and/or Client Registration
                .oauth2ResourceServer((resourceServer) -> resourceServer
                        .jwt(Customizer.withDefaults()))
                .build();
    }

    /**
     * 2. A Spring Security filter chain for authentication.
     * @param http httpSecurity
     * @return a security filter chain
     * @throws Exception exception
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultWebSecurityFilterChain(HttpSecurity http)
            throws Exception {
        log.info("# defaultWebSecurityFilterChain() bean called");
        return http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated())
                // Form login handles the redirect to the login page from the
                // authorization server filter chain
                .formLogin(Customizer.withDefaults())
                .build();
    }

    /**
     * 3. An instance of UserDetailsService for retrieving users to authenticate.
     * As we use now JPA and MySQL, it's no longer required
     * @return a userDetailsService basic instance
     */
    @Bean
    public UserDetailsService userDetailsService() {
        log.info("# userDetailsService() bean called");
        log.info("Configuring UserDetailsService");
        UserDetails userDetails = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(userDetails);
    }

    /**
     * 4. An instance of RegisteredClientRepository for managing clients.
     * @return registeredClientRepository
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        log.info("# registeredClientRepository() bean called");
        RegisteredClient oidcClient = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId("oidc-client")
                .clientSecret(passwordEncoder.encode("secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("https://oauthdebugger.com/debug")
                .postLogoutRedirectUri("http://127.0.0.1:9000/")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .clientSettings(clientSettings())
                .build();

        return new InMemoryRegisteredClientRepository(oidcClient);
    }

    @Bean
    public ClientSettings clientSettings() {
        return ClientSettings.builder()
                //.requireAuthorizationConsent(Boolean.TRUE)
                .requireProofKey(Boolean.TRUE)
                .build();
    }

    /**
     * 5. An instance of com.nimbusds.jose.jwk.source.JWKSource for signing access tokens.
     * @return JWKSource
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        log.info("Configuring JWKSource");
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    /**
     * 6. An instance of java.security.KeyPair with keys generated on startup used to create the JWKSource above.
     * @return KeyPair
     */
    private static KeyPair generateRsaKey() {
        log.info("Configuring generateRsaKey");
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        }
        catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    /**
     * 7. An instance of JwtDecoder for decoding signed access tokens.
     * @param jwkSource JWKSource
     * @return JwtDecoder
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        log.info("Configuring JwtDecoder");
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    /**
     * 8. An instance of AuthorizationServerSettings to configure Spring Authorization Server.
     * @return AuthorizationServerSettings
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        log.info("Configuring authorizationServerSettings");
        return AuthorizationServerSettings.builder()
                .issuer("http://localhost:9000")
                .build();
    }
}