package eu.dreamlabs.usersservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                                .anyRequest().permitAll()).build();
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/**").access((authentication, context) -> {
                            String remoteAddr = context.getRequest().getRemoteAddr();
                            return remoteAddr.equals("127.0.0.1") ?
                                    new AuthorizationDecision(true) : new AuthorizationDecision(false);
                        })
                        //.requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //.headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer
                //        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .build();
    }
}
