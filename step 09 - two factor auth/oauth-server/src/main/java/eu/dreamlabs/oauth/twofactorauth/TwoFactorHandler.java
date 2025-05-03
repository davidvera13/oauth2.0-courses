package eu.dreamlabs.oauth.twofactorauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import java.io.IOException;

public class TwoFactorHandler implements AuthenticationSuccessHandler {
    private final SecurityContextRepository securityContextRepository;
    private final Authentication anonymousAuthenticationToken;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final TwoFactorAuthService twoFactorAuthService;

    public TwoFactorHandler(
            TwoFactorAuthService twoFactorAuthService) {
        // we have 2fa.html page in resources / template
        SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler =
                new SimpleUrlAuthenticationSuccessHandler("/2fa");
        authenticationSuccessHandler.setAlwaysUseDefaultTargetUrl(Boolean.TRUE);
        this.authenticationSuccessHandler = authenticationSuccessHandler;

        this.twoFactorAuthService = twoFactorAuthService;
        this.securityContextRepository = new HttpSessionSecurityContextRepository();
        this.anonymousAuthenticationToken = new AnonymousAuthenticationToken(
                "anonymous",
                "anonymous",
                AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS", "ROLE_2FA"));
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {
        twoFactorAuthService.setAuthentication(authentication);
        setAuthentication(request, response);
        this.authenticationSuccessHandler
                .onAuthenticationSuccess(request, response, anonymousAuthenticationToken);
    }

    private void setAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(anonymousAuthenticationToken);
        SecurityContextHolder.setContext(securityContext);
        securityContextRepository.saveContext(securityContext, request, response);
    }
}
