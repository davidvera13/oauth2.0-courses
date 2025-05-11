package eu.dreamlabs.photos.controllers;

import java.util.Arrays;
import java.util.List;

import eu.dreamlabs.photos.domains.PhotoResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/photos")
public class PhotosController {

    @GetMapping
    public List<PhotoResponse> getPhotos() {

        PhotoResponse photo1 = PhotoResponse.builder()
                .albumId("1")
                .photoId("1")
                .userId("1")
                .photoTitle("Title 1")
                .photoDescription("Description 1")
                .photoUrl("url 1")
                .build();
        PhotoResponse photo2 = PhotoResponse.builder()
                .albumId("2")
                .photoId("2")
                .userId("1")
                .photoTitle("Title 2")
                .photoDescription("Description 2")
                .photoUrl("url 2")
                .build();

        return Arrays.asList(photo1, photo2);
    }

}