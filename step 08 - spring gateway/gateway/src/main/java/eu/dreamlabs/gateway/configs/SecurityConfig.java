package eu.dreamlabs.gateway.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public SecurityWebFilterChain securityFilterChain(
            ServerHttpSecurity http) throws Exception {
        http
                .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                        .pathMatchers("/**").permitAll()
                        .anyExchange().authenticated())
                        //.pathMatchers("/v1/api/resources").authenticated()
                        //.anyExchange().permitAll()) // Angular itself is public
                .oauth2Login(Customizer.withDefaults());
        return http.build();

    }
}
