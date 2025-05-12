package eu.dreamlabs.usersservice.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class VerifyPasswordResponse {
    private boolean result;
}
