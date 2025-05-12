package eu.dreamlabs.usersservice.controllers;

import eu.dreamlabs.usersservice.domains.UserResponse;
import eu.dreamlabs.usersservice.domains.VerifyPasswordResponse;
import eu.dreamlabs.usersservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService usersService;

    @GetMapping(
            value = "/{userName}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse getUser(@PathVariable("userName") String userName) {
        return usersService.getUserDetails(userName);

    }

    @PostMapping(
            value = "/{userName}/verify-password",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public VerifyPasswordResponse verifyUserPassword(
            @PathVariable("userName") String userName,
            @RequestBody String password) {
        password = password.replace("password=", "");
        VerifyPasswordResponse returnValue = new VerifyPasswordResponse(false);
        UserResponse user = usersService.getUserDetails(userName, password);

        if (user != null) {
            returnValue.setResult(true);
        }
        return returnValue;
    }

}
