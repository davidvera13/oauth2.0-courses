package eu.dreamlabs.oauth.configs;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.modelmapper.module.jsr310.Jsr310Module;
import org.modelmapper.module.jsr310.Jsr310ModuleConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Configuration
public class AppBeanConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        Jsr310ModuleConfig config = Jsr310ModuleConfig.builder()
                .dateTimeFormatter(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                .dateFormatter(DateTimeFormatter.ISO_LOCAL_DATE)
                .zoneId(ZoneOffset.UTC)
                .build();

        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setPreferNestedProperties(false)
                .setFieldMatchingEnabled(true)
                .setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        modelMapper.registerModule(new Jsr310Module(config));

        return modelMapper;
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new SavedRequestAwareAuthenticationSuccessHandler();
    }
}
