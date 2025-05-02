package eu.dreamlabs.oauth.services.impl;

import eu.dreamlabs.oauth.mappers.ClientMapper;
import eu.dreamlabs.oauth.services.ClientService;
import eu.dreamlabs.oauth.entities.clients.ClientEntity;
import eu.dreamlabs.oauth.repositories.ClientRepository;
import eu.dreamlabs.oauth.shared.dto.MessageDto;
import eu.dreamlabs.oauth.shared.dto.clients.ClientDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MessageDto createClient(ClientDto request) {
        ClientEntity clientEntity = ClientMapper.clientDtoToEntity(request);
        clientEntity.setClientSecret(passwordEncoder.encode(request.getClientSecret()));
        clientRepository.save(clientEntity);
        return new MessageDto("Client created with id " + clientEntity.getClientId());
    }

    @Override
    public void save(RegisteredClient registeredClient) {

    }

    @Override
    public RegisteredClient findById(String id) {
        ClientEntity clientEntity = clientRepository.findById(Long.getLong(id)).orElseThrow(() ->
                new RuntimeException("No client found with id " + id));
        // note: test of we need toRegisterdClient method
        return toRegisteredClient(clientEntity);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        ClientEntity clientEntity = clientRepository.findByClientId(clientId).orElseThrow(() ->
                new RuntimeException("No client found with clientId: " + clientId));
        // note: test of we need toRegisterdClient method
        return toRegisteredClient(clientEntity);
    }


    @Override
    public RegisteredClient toRegisteredClient(ClientEntity client){
        Set<AuthorizationGrantType> authorizationGrantTypes = new HashSet<>();
        Set<ClientAuthenticationMethod> clientAuthenticationMethods = new HashSet<>();

        client.getAuthorizationGrantTypes().forEach(authMethod ->
                authorizationGrantTypes.add(new AuthorizationGrantType(authMethod)));

        client.getClientAuthenticationMethods().forEach(authMethod ->
                clientAuthenticationMethods.add(new ClientAuthenticationMethod(authMethod)));

        return RegisteredClient.withId(client.getClientId())
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .clientIdIssuedAt(new Date().toInstant())
                .clientAuthenticationMethods(am -> am.addAll(clientAuthenticationMethods))
                .authorizationGrantTypes(agt -> agt.addAll(authorizationGrantTypes))
                .redirectUris(ru -> ru.addAll(client.getRedirectUris()))
                .postLogoutRedirectUris(pl -> pl.addAll(client.getPostLogoutRedirectUris()))
                .scopes(sc -> sc.addAll(client.getScopes()))
                .clientSettings(ClientSettings
                        .builder()
                        .requireProofKey(client.isRequireProofKey())
                        .requireAuthorizationConsent(Boolean.TRUE)
                        .build())
                .build();
    }
}