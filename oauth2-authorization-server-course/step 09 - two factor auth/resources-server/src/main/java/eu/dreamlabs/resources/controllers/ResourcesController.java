package eu.dreamlabs.resources.controllers;

import eu.dreamlabs.resources.domains.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/resources")
public class ResourcesController {
    @GetMapping("/users")
    //@PreAuthorize("hasAuthority('ROLE_USER')") // by default user
    public ResponseEntity<MessageResponse> userMessage(Authentication auth) {
        System.out.println("Users entrypoint called");
        return ResponseEntity.ok(new MessageResponse("Hello " + auth.getName()));
    }

    @GetMapping("/admins")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<MessageResponse> adminMessage(Authentication auth) {
        System.out.println("Admin entrypoint called");
        return ResponseEntity.ok(new MessageResponse("Hello M. " + auth.getName()));
    }

    @GetMapping("/any")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER', 'OIDC_USER')")
    public ResponseEntity<MessageResponse> allMessage(Authentication auth) {
        System.out.println("All users entrypoint called");
        return ResponseEntity.ok(new MessageResponse("Hello you whoever is logged in " + auth.getName()));
    }
}
