package eu.dreamlabs.resources.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
        return "Working on port " + env.getProperty("server.port");
    }

    @GetMapping("/developer")
    public String getUserWithRoleDeveloper(@AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> claims = jwt.getClaims();
        System.out.println("----------------------------------------");
        claims.forEach((claimName, claimValue) -> {
            System.out.println(claimName + ": " + claimValue);
        });
        return "Working on port " + env.getProperty("server.port");
    }

    @GetMapping("/status")
    public String status() {
        return "Working on port " + env.getProperty("server.port");
    }
}
