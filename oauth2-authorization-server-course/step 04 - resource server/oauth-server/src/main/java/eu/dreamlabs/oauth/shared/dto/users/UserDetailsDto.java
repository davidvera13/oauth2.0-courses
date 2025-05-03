package eu.dreamlabs.oauth.shared.dto.users;

import lombok.*;

import java.util.List;

@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UserDetailsDto {
    private Long id;
    private String username;
    private String password;
    // default roles will be set
    private boolean accountExpired;
    private boolean accountLocked;
    private boolean credentialsExpired;
    private boolean disabled;

    private List<String> roles;


}