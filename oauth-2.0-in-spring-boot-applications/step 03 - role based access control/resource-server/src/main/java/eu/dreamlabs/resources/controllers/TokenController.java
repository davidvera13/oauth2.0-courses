package eu.dreamlabs.resources.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/tokens")
@RequiredArgsConstructor
public class TokenController {

    @GetMapping
    public Map<String, Object> getToken(@AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> claims = jwt.getClaims();
        //System.out.println("----------------------------------------");
        //claims.forEach((claimName, claimValue) -> {
        //    System.out.println(claimName + ": " + claimValue);
        //});
        String name = (String) claims.get("name");
        String token = jwt.getTokenValue();
        System.out.println("Hello " + name);
        System.out.println("Token " + token);
        return Collections.singletonMap("principal", jwt);
    }

    @GetMapping("/jwt")
    public Jwt getJwt(@AuthenticationPrincipal Jwt jwt) {
        return jwt;
    }
}
