package eu.dreamlabs.oauth.repositories;

import eu.dreamlabs.oauth.entities.clients.ClientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByClientId(String clientId);

}