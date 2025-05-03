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
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

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
                        authorizationServer.oidc(Customizer.withDefaults()))	// Enable OpenID Connect 1.0
                .authorizeHttpRequests((authorize) ->
                        authorize.anyRequest().authenticated())
                // Redirect to the login page when not authenticated from the authorization endpoint
                .exceptionHandling((exceptions) -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)))
                // Accept access tokens for User Info and/or Client Registration
                .oauth2ResourceServer((resourceServer) ->
                        resourceServer.jwt(Customizer.withDefaults()))
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
                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                        .requestMatchers(HttpMethod.POST, "/clients").permitAll()
                        .anyRequest().authenticated())
                // Form login handles the redirect to the login page from the authorization server filter chain
                .csrf(csrfConfigurer ->
                        csrfConfigurer.ignoringRequestMatchers("/auth", "/clients"))
                .formLogin(Customizer.withDefaults())
                .build();
    }

    /**
     * We want to define JWT with custom claims
     * @return OAuth2TokenCustomizer
     */
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(){
        return context -> {
            Authentication principalUser = context.getPrincipal();
            // we intercept principal and read tokens: idToken & accessToken
            if(context.getTokenType().getValue().equals("id_token")) {
                context.getClaims().claim("token_type", "id token");
            }
            if(context.getTokenType().getValue().equals("access_token")) {
                context.getClaims().claim("token_type", "access token");
                // TODO: improve to get also role + authorities
                Set<String> roles = principalUser.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet());
                context.getClaims()
                        .claim("roles", roles)
                        .claim("username", principalUser.getName());
            }
        };
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