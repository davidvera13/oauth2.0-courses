package eu.dreamlabs.resources.domains;

import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UserResponse {
    private String userId;
    private String firstName;
    private String lastName;
}
