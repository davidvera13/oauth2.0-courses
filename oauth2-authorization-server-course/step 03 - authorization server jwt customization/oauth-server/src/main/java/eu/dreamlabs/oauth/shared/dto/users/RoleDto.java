package eu.dreamlabs.oauth.shared.dto.users;

import eu.dreamlabs.oauth.shared.enums.RoleName;
import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class RoleDto {
    private RoleName name;
}