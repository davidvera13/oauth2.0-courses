package eu.dreamlabs.photos.configs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> realmAccess = objectMapper.convertValue(
                jwt.getClaims().get("realm_access"),
                new TypeReference<>() {});

        realmAccess.put("scope", Arrays.stream(jwt.getClaims().get("scope").toString().split(" ")).toList());
        //if (realmAccess == null || realmAccess.isEmpty())
        // return new ArrayList<>();
        List<String> roles = objectMapper.convertValue(
                realmAccess.get("roles"),
                new TypeReference<>() {});

        List<String> scopes = objectMapper.convertValue(
                realmAccess.get("scope"),
                new TypeReference<>() {});

        Collection<GrantedAuthority> grantedRoles = roles
                .stream()
                .map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        Collection<GrantedAuthority> grantedScopes = scopes
                .stream()
                .map(scope -> "SCOPE_" + scope)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return Stream.of(grantedRoles, grantedScopes)
                .flatMap(Collection::stream)
                .toList();
    }
}