package eu.dreamlabs.oauth.services;

import eu.dreamlabs.oauth.shared.dto.MessageDto;
import eu.dreamlabs.oauth.shared.dto.users.UserDetailsDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService  extends UserDetailsService {
    MessageDto createUser(UserDetailsDto user);
    UserDetailsDto getUser(String email);
    UserDetailsDto getUserByUserId(String userId);
    UserDetailsDto updateUser(String userId, UserDetailsDto user);
    void deleteUser(String userId);
    List<UserDetailsDto> getUsers(int page, int limit);
    boolean verifyEmailToken(String token);
    boolean requestPasswordReset(String email);
    boolean resetPassword(String token, String password);
}