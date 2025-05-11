package eu.dreamlabs.photos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PhotosServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhotosServiceApplication.class, args);
    }

}
