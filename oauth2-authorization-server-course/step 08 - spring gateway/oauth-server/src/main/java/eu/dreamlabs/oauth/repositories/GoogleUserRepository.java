package eu.dreamlabs.oauth.repositories;

import eu.dreamlabs.oauth.entities.users.GoogleUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoogleUserRepository extends JpaRepository<GoogleUserEntity, Long> {
    Optional<GoogleUserEntity> findByEmail(String email);
}