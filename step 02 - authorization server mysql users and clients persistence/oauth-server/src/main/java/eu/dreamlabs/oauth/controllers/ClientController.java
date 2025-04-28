package eu.dreamlabs.oauth.controllers;

import eu.dreamlabs.oauth.domains.request.ClientCreateRequest;
import eu.dreamlabs.oauth.domains.response.MessageResponse;
import eu.dreamlabs.oauth.mappers.ClientMapper;
import eu.dreamlabs.oauth.services.ClientService;
import eu.dreamlabs.oauth.shared.dto.MessageDto;
import eu.dreamlabs.oauth.shared.dto.clients.ClientDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final ModelMapper modelMapper;

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<MessageResponse> createClient(@RequestBody ClientCreateRequest client) {
        ClientDto clientDto = ClientMapper.clientRequestToDto(client);
        MessageDto messageDto = clientService.createClient(clientDto);
        MessageResponse response = modelMapper.map(messageDto, MessageResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}

