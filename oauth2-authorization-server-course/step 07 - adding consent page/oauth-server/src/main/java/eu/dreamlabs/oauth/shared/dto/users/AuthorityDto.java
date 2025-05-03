package eu.dreamlabs.oauth.shared.dto.users;

import lombok.*;

import java.util.Collection;

@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class AuthorityDto {
    private String name;
    private Collection<RoleDto> roles;

}