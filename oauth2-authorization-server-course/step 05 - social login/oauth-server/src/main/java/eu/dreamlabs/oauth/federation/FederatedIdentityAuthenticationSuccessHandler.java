package eu.dreamlabs.oauth.federation;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.function.Consumer;

@Getter @Setter
public final class FederatedIdentityAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {
    private final AuthenticationSuccessHandler delegate;
    private Consumer<OAuth2User> oauth2UserHandler;;
    private Consumer<OidcUser> oidcUserHandler;

    public FederatedIdentityAuthenticationSuccessHandler() {
        this.delegate = new SavedRequestAwareAuthenticationSuccessHandler();
        this.oauth2UserHandler = (user) -> {};
        this.oidcUserHandler = (user) -> this.oauth2UserHandler.accept(user);
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof OAuth2AuthenticationToken) {
            if (authentication.getPrincipal() instanceof OidcUser) {
                this.oidcUserHandler.accept((OidcUser) authentication.getPrincipal());
            } else if (authentication.getPrincipal() instanceof OAuth2User) {
                this.oauth2UserHandler.accept((OAuth2User) authentication.getPrincipal());
            }
        }

        this.delegate.onAuthenticationSuccess(request, response, authentication);
    }


    //public void setOAuth2UserHandler(Consumer<OAuth2User> oauth2UserHandler) {
    //    this.oauth2UserHandler = oauth2UserHandler;
    //}

    //public void setOidcUserHandler(Consumer<OidcUser> oidcUserHandler) {
    //    this.oidcUserHandler = oidcUserHandler;
    //}

}
