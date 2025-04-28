package eu.dreamlabs.oauth.bootstrap;

import eu.dreamlabs.oauth.entities.clients.ClientEntity;
import eu.dreamlabs.oauth.entities.users.RoleEntity;
import eu.dreamlabs.oauth.entities.users.UserEntity;
import eu.dreamlabs.oauth.repositories.AuthorityRepository;
import eu.dreamlabs.oauth.repositories.ClientRepository;
import eu.dreamlabs.oauth.repositories.RoleRepository;
import eu.dreamlabs.oauth.repositories.UserRepository;
import eu.dreamlabs.oauth.shared.dto.users.RoleDto;
import eu.dreamlabs.oauth.shared.enums.RoleName;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class ConfigureDataSample implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        Optional<ClientEntity> client = clientRepository.findByClientId("oidc-client");
        if(client.isEmpty()) {
            //ClientEntity oidc = clientRepository.findByClientId("oidc-client")
            //        .orElseThrow(() -> new RuntimeException("No client found"));
            //clientRepository.delete(oidc);
            ClientEntity clientEntity = setClientEntity();
            clientRepository.save(clientEntity);
        }

        if(roleRepository.count() == 0) {
            RoleEntity adminRole = modelMapper.map(
                    RoleDto.builder().name(RoleName.ROLE_ADMIN).build(),
                    RoleEntity.class);

            RoleEntity userRole = modelMapper.map(
                    RoleDto.builder().name(RoleName.ROLE_USER).build(),
                    RoleEntity.class);
            roleRepository.save(adminRole);
            roleRepository.save(userRole);

            UserEntity user = new UserEntity();
            user.setUsername("user@mail.com");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRoles(List.of(userRole));
            userRepository.save(user);

            UserEntity admin = new UserEntity();
            admin.setUsername("admin@mail.com");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setRoles(List.of(adminRole));
            userRepository.save(admin);

            UserEntity any = new UserEntity();
            any.setUsername("any@mail.com");
            any.setPassword(passwordEncoder.encode("password"));
            any.setRoles(List.of(userRole, adminRole));
            userRepository.save(any);
        }

    }

    private ClientEntity setClientEntity() {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setClientId("oidc-client");
        clientEntity.setClientSecret(passwordEncoder.encode("secret"));
        clientEntity.setClientAuthenticationMethods(
                Set.of("client_secret_basic"));
        //Set.of(new ClientAuthenticationMethod("client_secret_basic")));
        clientEntity.setAuthorizationGrantTypes(
                Set.of(
                        "authorization_code",
                        "refresh_token",
                        "client_credentials"
                ));
        //Set.of(
        //        new AuthorizationGrantType("authorization_code"),
        //        new AuthorizationGrantType("refresh_token"),
        //        new AuthorizationGrantType("client_credentials")
        //        ));
        clientEntity.setScopes(Set.of("openid"));
        clientEntity.setRequireProofKey(Boolean.TRUE);
        clientEntity.setRedirectUris(Set.of("https://oauthdebugger.com/debug"));
        // clientEntity.setRedirectUris(Set.of("http://localhost:4200/authorized"));
        return clientEntity;
    }
}
