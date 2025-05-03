package eu.dreamlabs.oauth.controllers;

import eu.dreamlabs.oauth.twofactorauth.TwoFactorAuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class TwoFactorController {
    private final SecurityContextRepository securityContextRepository;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final TwoFactorAuthService twoFactorAuthService;

    public TwoFactorController(
            AuthenticationSuccessHandler authenticationSuccessHandler,
            TwoFactorAuthService twoFactorAuthService) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.twoFactorAuthService = twoFactorAuthService;
        this.securityContextRepository =
                new HttpSessionSecurityContextRepository();
        this.authenticationFailureHandler =
                new SimpleUrlAuthenticationFailureHandler("/2fa?error");
    }

    @GetMapping("/2fa")
    public String twoFactor() {
        return "2fa";
    }

    @PostMapping("/2fa")
    public void validateCode(
            @RequestParam("code") String code,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        // note: we could / should be able to send code using sms, mail etc and retrieve it from db
        System.out.println("code: " + code);
        if (code.equals("abcd"))
            this.authenticationSuccessHandler
                    .onAuthenticationSuccess(
                            request,
                            response,
                            getAuthentication(request, response));
        else
            this.authenticationFailureHandler
                    .onAuthenticationFailure(
                            request,
                            response,
                            new BadCredentialsException("Invalid code"));
    }

    private Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Authentication authentication = twoFactorAuthService.getAuthentication();
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
        securityContextRepository.saveContext(securityContext, request, response);
        return authentication;
    }
}