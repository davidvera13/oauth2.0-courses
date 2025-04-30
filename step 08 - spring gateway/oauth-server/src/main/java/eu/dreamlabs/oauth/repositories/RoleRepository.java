package eu.dreamlabs.oauth.repositories;

import eu.dreamlabs.oauth.entities.users.RoleEntity;
import eu.dreamlabs.oauth.shared.enums.RoleName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(RoleName name);
}