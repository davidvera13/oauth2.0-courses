package eu.dreamlabs.oauth.controllers;

import eu.dreamlabs.oauth.domains.request.UserCreateDetailsRequest;
import eu.dreamlabs.oauth.domains.response.MessageResponse;
import eu.dreamlabs.oauth.services.UserService;
import eu.dreamlabs.oauth.shared.dto.users.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<MessageResponse> createUser(@RequestBody UserCreateDetailsRequest user) {
        UserDetailsDto userDetailsDto = modelMapper.map(user, UserDetailsDto.class);
        userDetailsDto.setRoles(List.of("ROLE_USER"));
        MessageResponse response = modelMapper.map(userService.createUser(userDetailsDto), MessageResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}

