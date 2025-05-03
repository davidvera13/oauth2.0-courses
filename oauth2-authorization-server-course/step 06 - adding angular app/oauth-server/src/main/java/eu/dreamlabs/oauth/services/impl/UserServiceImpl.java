package eu.dreamlabs.oauth.services.impl;

import eu.dreamlabs.oauth.configs.UserPrincipal;
import eu.dreamlabs.oauth.entities.users.RoleEntity;
import eu.dreamlabs.oauth.entities.users.UserEntity;
import eu.dreamlabs.oauth.repositories.RoleRepository;
import eu.dreamlabs.oauth.repositories.UserRepository;
import eu.dreamlabs.oauth.services.UserService;
import eu.dreamlabs.oauth.shared.dto.MessageDto;
import eu.dreamlabs.oauth.shared.dto.users.UserDetailsDto;
import eu.dreamlabs.oauth.shared.enums.RoleName;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(UserPrincipal::new)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    @Override
    public MessageDto createUser(UserDetailsDto user) {
        // TODO: return a message in json with error message : Custom exception
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            throw new RuntimeException("User already exists");

        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<RoleEntity> roleEntities = new HashSet<>();
        user.getRoles().forEach(role -> {
            var stored = roleRepository.findByName(RoleName.valueOf(role))
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            roleEntities.add(stored);
        });
        userEntity.setRoles(roleEntities);
        userEntity = userRepository.save(userEntity);
        return MessageDto.builder()
                .message("User " + userEntity.getUsername() + " created successfully")
                .build();
    }

    @Override
    public UserDetailsDto getUser(String email) {
        return null;
    }

    @Override
    public UserDetailsDto getUserByUserId(String userId) {
        return null;
    }

    @Override
    public UserDetailsDto updateUser(String userId, UserDetailsDto user) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public List<UserDetailsDto> getUsers(int page, int limit) {
        return List.of();
    }

    @Override
    public boolean verifyEmailToken(String token) {
        return false;
    }

    @Override
    public boolean requestPasswordReset(String email) {
        return false;
    }

    @Override
    public boolean resetPassword(String token, String password) {
        return false;
    }
}