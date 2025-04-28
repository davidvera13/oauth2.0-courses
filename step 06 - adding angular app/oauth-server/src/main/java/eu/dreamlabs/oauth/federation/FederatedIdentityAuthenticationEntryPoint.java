package eu.dreamlabs.oauth.federation;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
@Getter @Setter
public final class FederatedIdentityAuthenticationEntryPoint
        implements AuthenticationEntryPoint {
    private final AuthenticationEntryPoint delegate;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final RedirectStrategy redirectStrategy;
    private String authorizationRequestUri;


    public FederatedIdentityAuthenticationEntryPoint(String loginPageUrl, ClientRegistrationRepository clientRegistrationRepository) {
        this.redirectStrategy = new DefaultRedirectStrategy();
        this.delegate = new LoginUrlAuthenticationEntryPoint(loginPageUrl);
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.authorizationRequestUri = OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI
                + "/{registrationId}";
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authenticationException)
            throws IOException, ServletException {
        String idp = request.getParameter("idp");
        if (idp != null) {
            ClientRegistration clientRegistration = this.clientRegistrationRepository.findByRegistrationId(idp);
            if (clientRegistration != null) {
                //String redirectUri = UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(request))
                //        .replaceQuery(null)
                //        .replacePath(this.authorizationRequestUri)
                //       .buildAndExpand(clientRegistration.getRegistrationId())
                //        .toUriString();
                String redirectUri = UriComponentsBuilder.newInstance()
                        .uri(new ServletServerHttpRequest(request).getURI())
                        .replacePath(this.authorizationRequestUri)
                        .buildAndExpand(clientRegistration.getRegistrationId())
                        .toUriString();
                this.redirectStrategy.sendRedirect(request, response, redirectUri);
                return;
            }
        }

        this.delegate.commence(request, response, authenticationException);
    }

    //public void setAuthorizationRequestUri(String authorizationRequestUri) {
    //    this.authorizationRequestUri = authorizationRequestUri;
    //}
}