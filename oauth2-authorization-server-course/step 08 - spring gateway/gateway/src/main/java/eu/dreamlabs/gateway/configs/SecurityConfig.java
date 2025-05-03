package eu.dreamlabs.gateway.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final ReactiveClientRegistrationRepository repository;

    @Bean
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public SecurityWebFilterChain securityFilterChain(
            ServerHttpSecurity http) throws Exception {
        http
                .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                        .pathMatchers("/**").permitAll()
                        .anyExchange().authenticated())
                .oauth2Login(Customizer.withDefaults())
                .oauth2Client(Customizer.withDefaults())
                .logout(logoutSpec -> logoutSpec
                        .logoutUrl("/logout")
                        //.logoutSuccessHandler(serverLogoutSuccessHandler(repository)));
                        .logoutSuccessHandler(customLogoutSuccessHandler()));
        return http.build();
    }

    @Bean
    public ServerLogoutSuccessHandler customLogoutSuccessHandler() {
        return new CustomLogoutSuccessHandler("http://127.0.0.1:8765/logged-out");
    }

    //ServerLogoutSuccessHandler serverLogoutSuccessHandler(ReactiveClientRegistrationRepository repository) {
    //    OidcClientInitiatedServerLogoutSuccessHandler successHandler =
    //            new OidcClientInitiatedServerLogoutSuccessHandler(repository);
    //    successHandler.setLogoutSuccessUrl(URI.create("http://localhost:9000/logout"));
    //    successHandler.setPostLogoutRedirectUri("{baseUrl}/logged-out");
    //    return successHandler;
    //}
}
