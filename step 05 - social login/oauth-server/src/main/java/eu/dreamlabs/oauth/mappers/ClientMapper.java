package eu.dreamlabs.oauth.mappers;

import eu.dreamlabs.oauth.domains.request.ClientCreateRequest;
import eu.dreamlabs.oauth.entities.clients.ClientEntity;
import eu.dreamlabs.oauth.shared.dto.clients.ClientDto;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.HashSet;
import java.util.Set;

public class ClientMapper {
    public static ClientDto clientRequestToDto(ClientCreateRequest client) {
        Set<ClientAuthenticationMethod> authenticationMethods = new HashSet<>();
        Set<AuthorizationGrantType> authorizationGrantTypes = new HashSet<>();
        client.getAuthenticationMethods().forEach(authenticationMethod -> {
            authenticationMethods.add(new ClientAuthenticationMethod(authenticationMethod));
        });
        client.getAuthorizationGrantTypes().forEach(authorizationGrantType -> {
            authorizationGrantTypes.add(new AuthorizationGrantType(authorizationGrantType));
        });

        return ClientDto.builder()
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .clientAuthenticationMethods(authenticationMethods)
                .authorizationGrantTypes(authorizationGrantTypes)
                .redirectUris(client.getRedirectUris())
                .scopes(client.getScopes())
                .requireProofKey(client.getRequireProofKey() == null ? Boolean.FALSE : Boolean.TRUE)
                .build();
    }

    public static ClientEntity clientDtoToEntity(ClientDto client) {
        Set<String> authenticationMethods = new HashSet<>();
        Set<String> authorizationGrantTypes = new HashSet<>();
        client.getClientAuthenticationMethods().forEach(authenticationMethod -> {
            authenticationMethods.add(authenticationMethod.getValue());
        });
        client.getAuthorizationGrantTypes().forEach(authorizationGrantType -> {
            authorizationGrantTypes.add(authorizationGrantType.getValue());
        });

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setClientId(client.getClientId());
        //clientEntity.setClientSecret(client.getClientSecret());
        clientEntity.setClientAuthenticationMethods(authenticationMethods);
        clientEntity.setAuthorizationGrantTypes(authorizationGrantTypes);
        clientEntity.setRedirectUris(client.getRedirectUris());
        clientEntity.setRequireProofKey(client.isRequireProofKey());
        clientEntity.setScopes(client.getScopes());
        return clientEntity;
    }
}
