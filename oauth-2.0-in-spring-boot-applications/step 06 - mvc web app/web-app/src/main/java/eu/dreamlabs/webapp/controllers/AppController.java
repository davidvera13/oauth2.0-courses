package eu.dreamlabs.webapp.controllers;

import eu.dreamlabs.webapp.domains.AlbumResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AppController {
    private final OAuth2AuthorizedClientService oauth2ClientService;
    private final RestTemplate restTemplate;

    @GetMapping("/albums")
    public String getAlbums(
            Model model) {
            // @AuthenticationPrincipal OidcUser principal) {
        //
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2AuthorizedClient oauth2Client = oauth2ClientService.loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(),
                oauthToken.getName());
        String jwtAccessToken = oauth2Client.getAccessToken().getTokenValue();
        System.out.println("jwtAccessToken = " + jwtAccessToken);
        //System.out.println("Principal = " + principal);
        //OidcIdToken idToken = principal.getIdToken();
        //String idTokenValue = idToken.getTokenValue();
        //System.out.println("idTokenValue = " + idTokenValue);
        String url = "http://localhost:8765/api/albums";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtAccessToken);
        HttpEntity<List<AlbumResponse>> entity = new HttpEntity<>(headers);
        ResponseEntity<List<AlbumResponse>> responseEntity =  restTemplate.exchange(
                url, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {});
        List<AlbumResponse> albums = responseEntity.getBody();
        // case 1: testing display data in html
        //AlbumResponse album1 = AlbumResponse.builder()
        //        .albumId("a1")
        //        .userId("1")
        //        .albumTitle("Album 1")
        //        .albumDescription("Album 1 details")
        //        .albumUrl("http://url1.com")
        //        .build();
        //AlbumResponse album2 = AlbumResponse.builder()
        //        .albumId("a2")
        //        .userId("2")
        //        .albumTitle("Album 2")
        //        .albumDescription("Album 2 details")
        //        .albumUrl("http://url2.com")
        //        .build();
        //model.addAttribute("albums", Arrays.asList(album1, album2));
        model.addAttribute("albums", albums);
        return "albums";
    }
}
