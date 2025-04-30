package eu.dreamlabs.oauth.domains.request;

import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UserCreateDetailsRequest {
    private String username;
    private String password;
}
