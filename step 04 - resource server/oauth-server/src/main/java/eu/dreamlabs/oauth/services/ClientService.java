package eu.dreamlabs.oauth.services;

import eu.dreamlabs.oauth.shared.dto.MessageDto;
import eu.dreamlabs.oauth.shared.dto.clients.ClientDto;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

public interface ClientService extends RegisteredClientRepository {
    MessageDto createClient(ClientDto request);
}
