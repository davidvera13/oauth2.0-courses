package eu.dreamlabs.albums.controllers;

import eu.dreamlabs.albums.domains.AlbumResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/albums")
public class AlbumsController {

    @GetMapping
    public List<AlbumResponse> getAlbums() {

        AlbumResponse album1 = AlbumResponse.builder()
                .albumId("a1")
                .userId("1")
                .albumTitle("Album 1")
                .albumDescription("Album 1 details")
                .albumUrl("http://url1.com")
                .build();

        AlbumResponse album2 = AlbumResponse.builder()
                .albumId("a2")
                .userId("2")
                .albumTitle("Album 2")
                .albumDescription("Album 2 details")
                .albumUrl("http://url2.com")
                .build();

        return Arrays.asList(album1, album2);
    }

}