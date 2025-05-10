package eu.dreamlabs.resources.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final Environment env;

    @GetMapping("/status")
    public String status() {
        return "Working on port " + env.getProperty("server.port");
    }
}
