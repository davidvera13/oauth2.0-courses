package eu.dreamlabs.resources.controllers;

import eu.dreamlabs.resources.domains.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final Environment env;

    @GetMapping
    public String getUserWithScopeProfile(@AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> claims = jwt.getClaims();
        System.out.println("----------------------------------------");
        claims.forEach((claimName, claimValue) -> {
            System.out.println(claimName + ": " + claimValue);
        });
        return "Working on port " + env.getProperty("local.server.port");
    }

    @GetMapping("/developer")
    public String getUserWithRoleDeveloper(@AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> claims = jwt.getClaims();
        System.out.println("----------------------------------------");
        claims.forEach((claimName, claimValue) -> {
            System.out.println(claimName + ": " + claimValue);
        });
        return "Working on port " + env.getProperty("local.server.port");
    }

    @Secured("ROLE_DEVELOPER")
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        return "Deleted user with id " + id;
    }

    @Secured("ROLE_DEVELOPER")
    @GetMapping("/auth-context")
    public String getUserAuthenticationContext() {
        // Note: to get user, we can pass AuthenticationPrincipal annotation with JWT or Authentication
        // OR : call SecurityContextHolder to get user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principalName = authentication.getName();
        System.out.println("Hello " + principalName);
        return "getUser " + authentication;
    }

    @Secured("ROLE_DEVELOPER")
    @GetMapping("/auth")
    public String getUserAuthentication(Principal authentication) {
        // Note: to get user, we can pass AuthenticationPrincipal annotation with JWT or Authentication
        // OR : call SecurityContextHolder to get user
        System.out.println("Hello " + authentication);
        return "getUserAuthentication called " + authentication;
    }

    // uses SPEL
    @PreAuthorize("hasRole('DEVELOPER')")
    @GetMapping("/pre")
    public String getUserPreAuth() {
        return "Pre Authorized ...";
    }

    // if we decode jwt, id is stored in 'sub'
    @PreAuthorize("hasRole('ADMIN') or #id == #jwt.subject")
    @DeleteMapping("/{id}/owner")
    public String deleteUserByOwner(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("id") String id) {
        return "Deleted user with id " + id + " and sub " + jwt.getSubject();
    }


    //@PostAuthorize("hasRole('DEVELOPER')")
    @PostAuthorize("hasAuthority('ROLE_DEVELOPER')")
    @GetMapping("/post")
    public String getUserPostAuth() {
        System.out.println("I do some checks ... ");
        return "Post Authorized ...";
    }



    @PostAuthorize("hasAuthority('ROLE_UNEXISTINGROLE')")
    @GetMapping("/post-fail")
    public String getUserPostAuthFail() {
        System.out.println("I do some checks ... ");
        return "Post Authorized ...";
    }

    @PostAuthorize("returnObject.userId == #jwt.subject")
    @GetMapping("/{id}")
    public UserResponse getUserByUserId(
            @PathVariable("id") String id,
            @AuthenticationPrincipal Jwt jwt) {
        return UserResponse.builder()
                .userId(jwt.getClaimAsString("sub"))
                .firstName(jwt.getClaimAsString("given_name"))
                .lastName(jwt.getClaimAsString("family_name"))
                .build();
    }

    @GetMapping("/status")
    public String status() {
        return env.getProperty("spring.application.name") + "Working on port " + env.getProperty("local.server.port");
    }
}
