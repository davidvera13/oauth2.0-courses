package eu.dreamlabs.oauth.twofactorauth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
@Getter @Setter
public class TwoFactorAuthService {
    private Authentication authentication;
}